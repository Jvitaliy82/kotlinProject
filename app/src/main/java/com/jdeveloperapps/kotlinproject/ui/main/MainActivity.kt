package com.jdeveloperapps.kotlinproject.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.jdeveloperapps.kotlinproject.R
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseActivity
import com.jdeveloperapps.kotlinproject.ui.note.NoteActivity
import com.jdeveloperapps.kotlinproject.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).run {
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()

    override val layoutRes: Int = R.layout.activity_main

    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter{
            NoteActivity.start(this, it.id)
        }
        rv_notes.adapter = adapter


        fab.setOnClickListener{
            NoteActivity.start(this)
        }
    }



    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this)
            .inflate(R.menu.main_menu, menu)?.let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
                LogoutDialog.newInstance {
                    AuthUI.getInstance()
                            .signOut(this)
                            .addOnCompleteListener {
                                startActivity(Intent(this, SplashActivity::class.java))
                                finish()
                            }
                }.show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
    }
}