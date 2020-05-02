package com.jdeveloperapps.kotlinproject.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jdeveloperapps.kotlinproject.R
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter{
            NoteActivity.start(this, it)
        }
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let { adapter.notes = it.notes }
        })

        fab.setOnClickListener{
            val note = Note(
                    UUID.randomUUID().toString(),
                    "Новая заметка",
                    "",
                    Note.Color.WHITE,
                    Date()
            )
            NoteActivity.start(this, note)
        }
    }


}