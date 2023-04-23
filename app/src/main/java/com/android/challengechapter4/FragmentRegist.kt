package com.android.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.challengechapter4.databinding.FragmentRegistBinding

class FragmentRegist : Fragment() {

    lateinit var binding: FragmentRegistBinding
    lateinit var sharedRegis: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedRegis = requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)

        binding.btnDaftar.setOnClickListener {
            var getFullname = binding.etName.text.toString()
            var getUsername = binding.etUname.text.toString()
            var getPwd = binding.etPwd.text.toString()
            var getRePwd = binding.etConPwd.text.toString()

            var addUser = sharedRegis.edit()
            addUser.putString("name", getFullname)
            addUser.putString("uname", getUsername)
            addUser.putString("pwd", getPwd)
            addUser.putString("repwd", getRePwd)
            if (getPwd == getRePwd) {
                addUser.apply()
                Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_fragmentRegist_to_fragmentLogin)
            } else {
                Toast.makeText(
                    context,
                    "Password yang anda inputkan tidak sama",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.tvLogin.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentRegist_to_fragmentLogin)
        }
    }
}