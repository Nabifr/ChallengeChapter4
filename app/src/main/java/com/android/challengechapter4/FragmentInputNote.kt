package com.android.challengechapter4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.challengechapter4.databinding.FragmentInputNoteBinding
import com.android.challengechapter4.room.NoteData
import com.android.challengechapter4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class FragmentInputNote : DialogFragment() {

    lateinit var binding : FragmentInputNoteBinding
    var dbNote : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInputNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbNote = NoteDatabase.getInstance(requireContext())

        binding.btnTambah.setOnClickListener{
            addNote()
        }
    }

    fun addNote(){
        GlobalScope.async {
            var title = binding.etJudul.text.toString()
            var content = binding.etKonten.text.toString()
            dbNote!!.noteDao().insertNote(NoteData(0, title, content))
            Toast.makeText(context, "Tambah Note berhasil", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }
    override fun onDetach() {
        super.onDetach()
        activity?.recreate()
    }
}