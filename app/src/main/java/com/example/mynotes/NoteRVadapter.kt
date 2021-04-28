package com.example.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVadapter(private val context: Context, private val listner: INotesRVAdaptor): RecyclerView.Adapter<NoteRVadapter.NoteviewHolder>() {

    val allNotes = ArrayList<Note>()

    inner class NoteviewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val textView: TextView = itemView.findViewById<TextView>(R.id.note_Tv)
        val deleteButton: ImageView = itemView.findViewById<ImageView>(R.id.delete_btn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteviewHolder {
        val viewHolder = NoteviewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listner.onItemClick(allNotes[viewHolder.adapterPosition])
        }
       return viewHolder

    }

    override fun onBindViewHolder(holder: NoteviewHolder, position: Int) {
        val currentPosition = allNotes[position]
        holder.textView.text = currentPosition.text
    }
    fun updatList(newList: List<Note>)
    {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
}
interface INotesRVAdaptor
{
    fun onItemClick(note: Note)
}