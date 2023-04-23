package com.android.challengechapter4

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.challengechapter4.databinding.FragmentHomeBinding
import com.android.challengechapter4.room.NoteData
import com.android.challengechapter4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var vmNote: NoteViewModel
    lateinit var adapterNote: NoteAdapter
    var dbNote: NoteDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.hdHome)

        sharedPreferences = requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)

        var getUser = sharedPreferences.getString("uname", "")
        binding.hdHome.title = "Welcome, $getUser"

        dbNote = NoteDatabase.getInstance(requireContext())

        noteVM()
        vmNote = ViewModelProvider(this).get(NoteViewModel::class.java)

        vmNote.getAllNoteObservers().observe(viewLifecycleOwner) {
            adapterNote.setData(it as ArrayList<NoteData>)
        }

        binding.btnFab.setOnClickListener {
            FragmentInputNote().show(childFragmentManager, "InputDialogFragment")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        inflater.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_ascending -> {
                getNoteAsc()
                Toast.makeText(context, "Sorted by Ascending", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.option_descending -> {
                getNoteDesc()
                Toast.makeText(context, "Sorted by Descending", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.option_logout -> {
                logout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun logout() {
        AlertDialog.Builder(context)
            .setTitle("Logout")
            .setMessage("Anda yakin ingin logout?")
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                val pref = sharedPreferences.edit()
                pref.clear()
                Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
            }.show()
    }

    fun noteVM() {
        adapterNote = NoteAdapter(ArrayList())
        binding.rvHome.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = adapterNote
    }

    fun getNoteDesc() {
        GlobalScope.launch {
            var data = dbNote?.noteDao()?.getNoteDesc()
            activity?.runOnUiThread {
                adapterNote = NoteAdapter(data!!)
                binding.rvHome.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvHome.adapter = adapterNote
            }
        }
    }

    fun getNoteAsc() {
        GlobalScope.launch {
            var data = dbNote?.noteDao()?.getNoteAsc()
            activity?.runOnUiThread {
                adapterNote = NoteAdapter(data!!)
                binding.rvHome.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvHome.adapter = adapterNote
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = dbNote?.noteDao()?.getNoteAsc()
            activity?.runOnUiThread {
                adapterNote = NoteAdapter(data!!)
                binding.rvHome.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvHome.adapter = adapterNote
            }
        }
    }
}