package com.jdeveloperapps.kotlinproject.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jdeveloperapps.kotlinproject.R
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseActivity
import com.jdeveloperapps.kotlinproject.ui.base.BaseViewModel
import com.jdeveloperapps.kotlinproject.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

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


}