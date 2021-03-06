package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete()
    suspend fun delete(note: Note)

    @Update()
    suspend fun update(note: Note)

    @Query(value = "Select * from NOTES_TABLE order by id ASC")
    fun  getAllNotes(): LiveData<List<Note>>

}