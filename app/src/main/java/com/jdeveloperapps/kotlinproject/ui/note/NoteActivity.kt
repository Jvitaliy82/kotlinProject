package com.jdeveloperapps.kotlinproject.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.jdeveloperapps.kotlinproject.R
import com.jdeveloperapps.kotlinproject.common.format
import com.jdeveloperapps.kotlinproject.common.getColorInt
import com.jdeveloperapps.kotlinproject.data.entity.Note
import com.jdeveloperapps.kotlinproject.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class NoteActivity : BaseActivity<NoteData>() {

    companion object {

        private const val EXTRA_NOTE = "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).apply {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    var color = Note.Color.WHITE
    private var note: Note? = null

    override val layoutRes: Int = R.layout.activity_note
    override val model: NoteViewModel by viewModel()

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let {
            model.loadNote(it)
        } ?: let {
            initView()
        }

    }

    override fun renderData(data: NoteData) {
        if (data.isDeleted) finish()
        this.note = data.note
        initView()
    }

    private fun initView() {

        supportActionBar?.title = note?.let {
            it.lastChange.format(DATE_FORMAT)
        } ?: getString(R.string.new_note_title)

        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let { note ->
            if (et_title.text.toString() != note.title) et_title.setText(note.title)
            if (et_body.text.toString() != note.text) et_body.setText(note.text)
            color = note.color
            toolbar.setBackgroundColor(note.color.getColorInt(this))
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)

        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(color.getColorInt(this))
            saveNote()
        }
    }

    private fun saveNote() {
        if (et_title == null || et_title.text!!.length < 3) return
        note = note?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChange = Date(),
                color = color
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

        note?.let {
            model.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.del_button -> deleteNote().let { true }
        R.id.note_pallete -> togglePallete().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    fun deleteNote() {
        AlertDialog.Builder(this)
                .setMessage(R.string.question_message)
                .setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(R.string.ok) { dialog, which -> model.deleteNote() }
                .show()
    }

    fun togglePallete() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

}