package uz.bismillah.ibadatiislamiya.ui.topic

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_topic.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.model.Topic
import uz.bismillah.ibadatiislamiya.ui.MainActivity
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment
import java.util.*

class TopicFragment : Fragment(R.layout.fragment_topic) {

    private val adapter = TopicListAdapter()
    private lateinit var dao: TopicDao
    private lateinit var preferences: SharedPreferences
    private var unitId = 1
    private lateinit var unitName: String
    private var keysShown = false
    private val args: TopicFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        dao = BookDatabase.getInstance(requireContext()).topicDao()

        unitId = args.unitId
        unitName = args.unitName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(unitName)

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        topicsRecyclerView.adapter = adapter
        topicsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        setData(unitId)

        adapter.setOnTopicItemClickListener { id, title ->
            preferences.edit().putInt(UnitFragment.LAST_READ, id).apply()

            findNavController().navigate(
                TopicFragmentDirections.actionTopicFragmentToQuestionAnswerFragment(topicId = id, topicName = title)
            )
        }

        topicSearchEditText.addTextChangedListener {
            val result : List<Topic> = dao.searchTopicByName(unitId, "%${it.toString()}%")
            adapter.models = result
        }

        topicSearchBar.setEndIconOnClickListener {
            topicSearchEditText.text?.clear()
        }

        qrKeyPressed(keyA)
        qrKeyPressed(keyG)
        qrKeyPressed(keyH)
        qrKeyPressed(keyQ)
        qrKeyPressed(keyN)
        qrKeyPressed(keyO)
        qrKeyPressed(keyU)
        qrKeyPressed(keyW)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_actionbar_with_keypads, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.keys -> {
                keysShown = !keysShown
                if (keysShown) {
                    cyrillicKeyPad.visibility = View.VISIBLE
                } else {
                    cyrillicKeyPad.visibility = View.GONE
                }
                true
            }
            else -> false
        }
    }

    private fun setData(unitId: Int) {
        adapter.models = dao.getTopicsByUnit(unitId)
    }

    private fun qrKeyPressed(view: View) {
        view.setOnClickListener {
            topicSearchEditText.text?.insert(topicSearchEditText.selectionStart, (it as TextView).text.toString().toLowerCase(Locale.ROOT))
        }
    }
}