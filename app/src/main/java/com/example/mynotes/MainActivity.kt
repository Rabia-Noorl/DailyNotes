package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRVAdaptor {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view.layoutManager = LinearLayoutManager(this)
        val adaptor = NoteRVadapter(this, this)
        recycler_view.adapter = adaptor

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {List->

            List?.let {
                adaptor.updatList(it)
            }

        })

    }

    override fun onItemClick(note: Note)
    {
        viewModel.deletNote(note)

        Toast.makeText(this,"${note.text} inserted" , Toast.LENGTH_LONG).show()
    }

    fun submitNote(view: View)
    {
        val noteText = edit_text.text.toString()
        if (noteText.isNotEmpty())
        {
            viewModel.insertNode(Note(noteText))
            Toast.makeText(this,"$noteText inserted" , Toast.LENGTH_LONG).show()
        }
    }
}