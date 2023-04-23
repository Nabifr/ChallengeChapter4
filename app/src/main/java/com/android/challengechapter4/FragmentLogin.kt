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
import com.android.challengechapter4.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var sharedLogin: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedLogin = requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)

        binding.tvRegist.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegist)
        }
        binding.btnLogin.setOnClickListener {
            var getDataUser = sharedLogin.getString("uname", "")
            var getDataPass = sharedLogin.getString("pwd", "")
            var uname = binding.etUname.text.toString()
            var pwd = binding.etPwd.text.toString()


            if (uname.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(context, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT)
                    .show()
            } else if (uname == getDataUser.toString() && pwd == getDataPass.toString()) {

                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else if (uname != getDataUser.toString() || pwd != getDataPass.toString()) {
                Toast.makeText(context, "Username dan Pasword yang anda masukan salah", Toast.LENGTH_SHORT).show()
            }

        }
    }

}