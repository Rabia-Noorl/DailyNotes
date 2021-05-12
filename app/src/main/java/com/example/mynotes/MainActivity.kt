package com.example.mynotes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_note.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), INotesRVAdaptor {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view.layoutManager = LinearLayoutManager(this)
        val adaptor = NoteRVadapter(this, this)
        recycler_view.adapter = adaptor

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { List ->

            List?.let {
                adaptor.updatList(it)
            }

        })

    }

    override fun onItemClick(note: Note)
    {
        viewModel.deletNote(note)
        Toast.makeText(this, "${note.text} delete successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onEditItemClick(note: Note) {
        val textViewt: TextView = note_Tv
        showEditNoteDialoge(textViewt)

        Toast.makeText(this, "${note.id} before", Toast.LENGTH_SHORT).show()
    }

    fun submitNote(view: View)
    {
        val noteText = edit_text.text.toString()
        if (noteText.isNotEmpty())
        {
            viewModel.insertNode(Note(noteText, currentDateTimes()))
            Toast.makeText(this, "$noteText inserted successfully", Toast.LENGTH_SHORT).show()

        }
        edit_text.setText("")
    }
    fun currentDateTimes(): String
    {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        return currentDate
    }

    private fun showEditNoteDialoge(tv: TextView)
    {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogelayout: View = inflater.inflate(R.layout.edit_note, null)
        val editText: EditText = dialogelayout.findViewById(R.id.noteEditText)

        with(builder){
            setTitle("Edit yout Note Please!")
            setPositiveButton("Ok"){dialog, which->
                tv.setText(editText.text.toString())
                val noteText = tv.text.toString()
                if (noteText.isNotEmpty())
                {

                }
            }
            setNegativeButton("Cancel"){dialog, which->
                Log.d("Main", "Negetive button clicked")
            }
            setView(dialogelayout)
            show()
        }

    }
}