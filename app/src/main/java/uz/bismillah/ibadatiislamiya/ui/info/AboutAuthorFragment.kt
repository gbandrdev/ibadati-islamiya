package uz.bismillah.ibadatiislamiya.ui.info

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_about_author.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.ui.MainActivity.Companion.TEXT_SIZE

class AboutAuthorFragment : Fragment(R.layout.fragment_about_author) {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorText.setTextSize(TypedValue.COMPLEX_UNIT_SP, preferences.getFloat(TEXT_SIZE, 18f))
    }
}