package com.android.challengechapter4
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.challengechapter4.databinding.FragmentSplashBinding

class FragmentSplash : Fragment() {
    lateinit var binding: FragmentSplashBinding
    lateinit var sharedSplash: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val view = binding.root

        Handler(Looper.myLooper()!!).postDelayed({

            sharedSplash = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

            if (sharedSplash.getString("uname", "").equals("")) {
                findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
            } else if (sharedSplash.getString("uname", "")!!.isNotEmpty()) {
                findNavController().navigate(R.id.action_fragmentSplash_to_fragmentHome)
            }
        }, 3000)
        return view
    }

}