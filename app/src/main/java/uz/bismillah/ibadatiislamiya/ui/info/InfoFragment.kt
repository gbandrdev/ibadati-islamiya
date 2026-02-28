package uz.bismillah.ibadatiislamiya.ui.info

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_info.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.ui.MainActivity.Companion.TEXT_SIZE

class InfoFragment : Fragment(R.layout.fragment_info) {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, preferences.getFloat(TEXT_SIZE, 18f))

        contactMeClick.progress = 0.7f
        contactTexnoPOSClick.progress = 0.7f
        contactPaziyletClick.progress = 0.7f

        contactMe.setOnClickListener {
            contactMeClick.setMinAndMaxFrame(0, 20)
            contactMeClick.playAnimation()
            directToTelegram("djakonystar")
        }

        contactTexnoPOS.setOnClickListener {
            contactMeClick.setMinAndMaxFrame(0, 20)
            contactTexnoPOSClick.playAnimation()
            directToTelegram("texnopos")
        }

        contactPaziylet.setOnClickListener {
            contactMeClick.setMinAndMaxFrame(0, 20)
            contactPaziyletClick.playAnimation()
            directToTelegram("paziyletuz")
        }

        aboutBookItem.setOnClickListener {
            findNavController().navigate(
                    InfoFragmentDirections.actionInfoFragmentToAboutBookFragment()
            )
        }

        aboutAuthorItem.setOnClickListener {
            findNavController().navigate(
                    InfoFragmentDirections.actionInfoFragmentToAboutAuthorFragment()
            )
        }
    }

    private fun directToTelegram(username: String) {
        val telegram = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://telegram.me/$username")
        )
        startActivity(telegram)
    }
}