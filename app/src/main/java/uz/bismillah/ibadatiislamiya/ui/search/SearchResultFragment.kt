package uz.bismillah.ibadatiislamiya.ui.search

import android.content.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_search_result.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer
import uz.bismillah.ibadatiislamiya.ui.MainActivity.Companion.TEXT_SIZE

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {
    private val adapter = SearchResultListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private val args: SearchResultFragmentArgs by navArgs()
    private lateinit var question: String
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()

        question = args.questionText

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchingResultRecyclerView.adapter = adapter
        setData()
        adapter.setTextSize(preferences.getFloat(TEXT_SIZE, 18f))

        adapter.setOnQuestionFavIconClickListener {
            setFavoriteQuestion(it)
        }

        adapter.setOnQuestionCopyIconClickListener { question, answer ->
            val answer1 = answer.replace("<b>", "")
            val answer2 = answer1.replace("</b>", "")
            val answer3 = answer2.replace("<p>", "\n")
            val answerFinal = answer3.replace("</p>", "")
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(question, "$question\n\n$answerFinal\n\n\n\"Ибадати Исламия\" китабынан")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Табыслы нусқаланды!", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnQuestionShareIconClickListener { question, answer ->
            val answer1 = answer.replace("<b>", "")
            val answer2 = answer1.replace("</b>", "")
            val answer3 = answer2.replace("<p>", "\n")
            val answerFinal = answer3.replace("</p>", "")

            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$question\n" +
                        "\n" +
                        "$answerFinal\n" +
                        "\n" +
                        "\n" +
                        "\"Ибадати Исламия\" китабынан")
                this.type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, resources.getString(R.string.app_name)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_actionbar_with_textsize_changer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.textSizeSettings -> {
                val view = requireActivity().findViewById<View>(R.id.textSizeSettings)
                val popupMenu = PopupMenu(requireContext(), view)
                popupMenu.menuInflater.inflate(R.menu.menu_textsize_change, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.smallTextSetting -> {
                            preferences.edit().putFloat(TEXT_SIZE, 14f).apply()
                            adapter.setTextSize(14f)
                            true
                        }
                        R.id.normalTextSetting -> {
                            preferences.edit().putFloat(TEXT_SIZE, 18f).apply()
                            adapter.setTextSize(18f)
                            true
                        }
                        R.id.largeTextSetting -> {
                            preferences.edit().putFloat(TEXT_SIZE, 22f).apply()
                            adapter.setTextSize(22f)
                            true
                        }
                        R.id.extraLargeTextSetting -> {
                            preferences.edit().putFloat(TEXT_SIZE, 26f).apply()
                            adapter.setTextSize(26f)
                            true
                        }
                        else -> false
                    }
                }

                popupMenu.show()
                true
            }
            else -> false
        }
    }

    private fun setData() {
        val result = mutableListOf<QuestionAnswer>()
        result.add(questionAnswerDao.getQuestionByQuestion(question))
        adapter.models = result
    }

    private fun setFavoriteQuestion(questionAnswer: QuestionAnswer) {
        questionAnswer.isFavorite = 1 - questionAnswer.isFavorite
        questionAnswerDao.updateQuestion(questionAnswer)
    }
}
