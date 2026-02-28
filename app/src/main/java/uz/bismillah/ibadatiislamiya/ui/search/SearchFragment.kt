package uz.bismillah.ibadatiislamiya.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.ui.MainActivity.Companion.TEXT_SIZE
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val adapter = SearchListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private var keysShown = false
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecyclerView.adapter = adapter
        searchRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        adapter.setTextSize(preferences.getFloat(TEXT_SIZE, 18f))

        searchEditText.addTextChangedListener {
            val result: List<String> = questionAnswerDao.searchQuestions("%${it.toString()}%")
            adapter.models = result
        }

        searchBar.setEndIconOnClickListener {
            searchEditText.text?.clear()
        }

        adapter.setOnSearchingResultClickListener {
            searchEditText.clearFocus()
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(it)
            )
        }

        qrKeyPressed(keyAInSearch)
        qrKeyPressed(keyGInSearch)
        qrKeyPressed(keyHInSearch)
        qrKeyPressed(keyQInSearch)
        qrKeyPressed(keyNInSearch)
        qrKeyPressed(keyOInSearch)
        qrKeyPressed(keyUInSearch)
        qrKeyPressed(keyWInSearch)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_actionbar_with_keypads, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.keys -> {
                keysShown = !keysShown
                if (keysShown) {
                    cyrillicKeyPadInSearch.visibility = View.VISIBLE
                } else {
                    cyrillicKeyPadInSearch.visibility = View.GONE
                }
                true
            }
            else -> false
        }
    }

    private fun qrKeyPressed(view: View) {
        view.setOnClickListener {
            searchEditText.text?.insert(searchEditText.selectionStart, (it as TextView).text.toString().toLowerCase(Locale.ROOT))
        }
    }
}
