package com.android.challengechapter4

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.challengechapter4.databinding.ItemNoteBinding
import com.android.challengechapter4.room.NoteData
import com.android.challengechapter4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.ArrayList

class NoteAdapter(var daftarNote: List<NoteData>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var dbNote: NoteDatabase? = null
    lateinit var vmNote: NoteViewModel

    class ViewHolder(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataBinding(itemData: NoteData) {
            binding.note = itemData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding(daftarNote[position])
        holder.binding.btnDeleteNote.setOnClickListener {
            dbNote = NoteDatabase.getInstance(it.context)

            // pembuatan variabel untuk proses penghapusan note
            val deleteNotes = LayoutInflater.from(it.context)
                .inflate(R.layout.pop_up_delete, null, false)
            val hapusDataDialog = AlertDialog.Builder(it.context)
                .setView(deleteNotes)
                .create()

            val buttonhapus = deleteNotes.findViewById<Button>(R.id.btnHapusPop)
            val buttoncancel = deleteNotes.findViewById<Button>(R.id.btnCancelPop)
            buttoncancel.setOnClickListener {
                hapusDataDialog.dismiss()
            }

            buttonhapus.setOnClickListener {
                Toast.makeText(it.context, "Note Telah Dihapus", Toast.LENGTH_SHORT).show()
                GlobalScope.async {

                    val command = dbNote?.noteDao()?.deleteNote(daftarNote[position])

                    (deleteNotes.context as MainActivity).runOnUiThread {
                        if (command != 0) {
                            Toast.makeText(
                                deleteNotes.context,
                                "Catatan berhasil dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                            (deleteNotes.context as MainActivity).recreate()
                        } else {
                            Toast.makeText(
                                deleteNotes.context,
                                "Catatan gagal dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
            hapusDataDialog.show()
        }

        holder.binding.btnEditNote.setOnClickListener {
            dbNote = NoteDatabase.getInstance(it.context)

            val editNotes = LayoutInflater.from(it.context)
                .inflate(R.layout.pop_up_edit, null, false)

            val inputeditJudul = editNotes.findViewById<EditText>(R.id.etJudulEdit)
            val inputeditContent = editNotes.findViewById<EditText>(R.id.etKontenEdit)
            val buttonEdit = editNotes.findViewById<Button>(R.id.btnSelesaiEdit)

            inputeditJudul.setText(daftarNote[position].title)
            inputeditContent.setText(daftarNote[position].content)

            AlertDialog.Builder(it.context)
                .setView(editNotes)
                .create()
                .show()

            buttonEdit.setOnClickListener {
                val hasilEditJudul = inputeditJudul.text.toString()
                val hasilEditContent = inputeditContent.text.toString()

                daftarNote[position].title = hasilEditJudul
                daftarNote[position].content = hasilEditContent
                GlobalScope.async {
                    val command = dbNote?.noteDao()?.updateNote(daftarNote[position])

                    (editNotes.context as MainActivity).runOnUiThread {
                        if (command != 0) {
                            Toast.makeText(
                                it.context,
                                "Catatan berhasil diupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                            (editNotes.context as MainActivity).recreate()
                        } else {
                            Toast.makeText(it.context, "Catatan gagal diupdate", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return daftarNote.size
    }

    fun setData(listNote: ArrayList<NoteData>) {
        this.daftarNote = listNote
    }
}