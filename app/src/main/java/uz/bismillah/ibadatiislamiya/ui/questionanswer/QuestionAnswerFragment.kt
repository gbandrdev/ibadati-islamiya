package uz.bismillah.ibadatiislamiya.ui.questionanswer

import android.content.*
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_question_answer.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.PrefixDao
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.model.Prefix
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer
import uz.bismillah.ibadatiislamiya.ui.MainActivity
import uz.bismillah.ibadatiislamiya.ui.MainActivity.Companion.TEXT_SIZE

class QuestionAnswerFragment : Fragment(R.layout.fragment_question_answer) {
    private val adapter = QuestionAnswerListAdapter()
    private lateinit var preferences: SharedPreferences
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private lateinit var prefixDao: PrefixDao
    private lateinit var topicDao: TopicDao
    private var topicId = 1
    private lateinit var topicName: String
    private val args: QuestionAnswerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()
        prefixDao = BookDatabase.getInstance(requireContext()).prefixDao()
        topicDao = BookDatabase.getInstance(requireContext()).topicDao()

        topicId = args.topicId
        topicName = args.topicName

        preferences = requireActivity().getSharedPreferences("Settings", MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(topicName)

        questionAnswerRecyclerView.adapter = adapter
        setData(topicId)
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
            val clip = ClipData.newPlainText(question, "$question\n\n$answerFinal\n\n\n\"Ибадати Исламия\" китабынан\n" +
                    "https://play.google.com/store/apps/details?id=uz.bismillah.ibadatiislamiya")
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
                        "\"Ибадати Исламия\" китабынан\n" +
                        "https://play.google.com/store/apps/details?id=uz.bismillah.ibadatiislamiya")
                this.type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, resources.getString(R.string.app_name)))
        }

        adapter.setOnPrefixFavIconClickListener {
            setFavoritePrefix(it)
        }

        adapter.setOnPrefixCopyIconClickListener {
            val prefix1 = it.replace("<b>", "")
            val prefix2 = prefix1.replace("</b>", "")
            val prefix3 = prefix2.replace("<p>", "\n")
            val prefixFinal = prefix3.replace("</p>", "")
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", "$prefixFinal\n\n\n\"Ибадати Исламия\" китабынан\n" +
                    "https://play.google.com/store/apps/details?id=uz.bismillah.ibadatiislamiya")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Табыслы нусқаланды!", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnPrefixShareIconClickListener {
            val prefix1 = it.replace("<b>", "")
            val prefix2 = prefix1.replace("</b>", "")
            val prefix3 = prefix2.replace("<p>", "\n")
            val prefixFinal = prefix3.replace("</p>", "")

            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$prefixFinal\n" +
                        "\n" +
                        "\n" +
                        "\"Ибадати Исламия\" китабынан\n" +
                        "https://play.google.com/store/apps/details?id=uz.bismillah.ibadatiislamiya")
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

    private fun setData(topicId: Int) {
        val models = mutableListOf<BaseModelQAPrefix>()

        if (topicDao.hasPrefix(topicId) == 1) {
            models.add(prefixDao.getPrefixByTopic(topicId))
        }
        models.addAll(questionAnswerDao.getQuestionsByTopic(topicId))

        adapter.models = models
    }

    private fun setFavoriteQuestion(questionAnswer: QuestionAnswer) {
        questionAnswer.isFavorite = 1 - questionAnswer.isFavorite
        questionAnswerDao.updateQuestion(questionAnswer)
    }

    private fun setFavoritePrefix(prefix: Prefix) {
        prefix.isFavorite = 1 - prefix.isFavorite
        prefixDao.updatePrefix(prefix)
    }
}
