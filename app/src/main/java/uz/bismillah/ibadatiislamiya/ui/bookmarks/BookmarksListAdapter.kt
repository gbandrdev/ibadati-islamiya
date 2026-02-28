package uz.bismillah.ibadatiislamiya.ui.bookmarks

import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_prefix.view.*
import kotlinx.android.synthetic.main.item_question_answer.view.*
import kotlinx.android.synthetic.main.item_question_answer.view.answerTextView
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.model.Prefix
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

class BookmarksListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var textSize = 18f

    var models = mutableListOf<BaseModelQAPrefix>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setTextSize(size: Float) {
        textSize = size
        notifyItemRangeChanged(0, models.size)
    }

    private var onQuestionFavIconClick: (questionAnswer: QuestionAnswer, position: Int) -> Unit = {questionAnswer, position ->  }
    fun setOnQuestionFavIconClickListener(onQuestionFavIconClick: (questionAnswer : QuestionAnswer, position: Int) -> Unit) {
        this.onQuestionFavIconClick = onQuestionFavIconClick
    }

    private var onQuestionCopyIconClick: (question: String, answer: String) -> Unit = { question, answer -> }
    fun setOnQuestionCopyIconClickListener(onQuestionCopyIconClick: (question: String, answer: String) -> Unit) {
        this.onQuestionCopyIconClick = onQuestionCopyIconClick
    }

    private var onQuestionShareIconClick: (question: String, answer: String) -> Unit = { question, answer -> }
    fun setOnQuestionShareIconClickListener(onQuestionShareIconClick: (question: String, answer: String) -> Unit) {
        this.onQuestionShareIconClick = onQuestionShareIconClick
    }

    private var onPrefixFavIconClick: (prefix: Prefix, position: Int) -> Unit = { prefix, position ->  }
    fun setOnPrefixFavIconClickListener(onPrefixFavIconClick: (prefix: Prefix, position: Int) -> Unit) {
        this.onPrefixFavIconClick = onPrefixFavIconClick
    }

    private var onPrefixCopyIconClick: (prefixText: String) -> Unit = { prefixText -> }
    fun setOnPrefixCopyIconClickListener(onPrefixCopyIconClick: (prefixText: String) -> Unit) {
        this.onPrefixCopyIconClick = onPrefixCopyIconClick
    }

    private var onPrefixShareIconClick: (prefixText: String) -> Unit = { prefixText -> }
    fun setOnPrefixShareIconClickListener(onPrefixShareIconClick: (prefixText: String) -> Unit) {
        this.onPrefixShareIconClick = onPrefixShareIconClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == BaseModelQAPrefix.PREFIX) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_prefix, parent, false)
            BookmarksPrefixListViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_question_answer, parent, false)
            BookmarksQuestionAnswerListViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (models[position].type == BaseModelQAPrefix.PREFIX) {
            (holder as BookmarksPrefixListViewHolder).populateModel(models[position] as Prefix, position)
        } else {
            (holder as BookmarksQuestionAnswerListViewHolder).populateModel(models[position] as QuestionAnswer, position)
        }
    }

    override fun getItemViewType(position: Int): Int = models[position].type

    override fun getItemCount(): Int = models.size

    fun removeItem(position: Int) {
        models.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, models.size)
    }

    inner class BookmarksQuestionAnswerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(questionAnswer: QuestionAnswer, position: Int) {
            itemView.questionTextView.text = questionAnswer.question
            itemView.answerTextView.text = HtmlCompat.fromHtml(questionAnswer.answer, HtmlCompat.FROM_HTML_MODE_COMPACT)

            itemView.questionTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            itemView.questionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            itemView.answerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            itemView.answerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

            var isFavorite = questionAnswer.isFavorite == 1

            if (isFavorite) {
                itemView.addToBookmark.progress = 0.44f
            } else {
                itemView.addToBookmark.progress = 0f
            }
            itemView.copy.progress = 0.67f
            itemView.share.progress = 0.67f

            itemView.addToBookmark.setOnClickListener {
                if (!isFavorite) {
                    itemView.addToBookmark.speed = 1f
                    itemView.addToBookmark.playAnimation()
                } else {
                    itemView.addToBookmark.speed = -1.7f
                    itemView.addToBookmark.setMinAndMaxFrame(0, 22)
                    itemView.addToBookmark.playAnimation()
                }
                isFavorite = !isFavorite
                onQuestionFavIconClick.invoke(questionAnswer, position)
            }

            itemView.copy.setOnClickListener {
                itemView.copy.setMinAndMaxFrame(0, 80)
                itemView.copy.speed = 2.6f
                itemView.copy.playAnimation()
                onQuestionCopyIconClick.invoke(questionAnswer.question, questionAnswer.answer)
            }

            itemView.share.setOnClickListener {
                itemView.share.setMinAndMaxFrame(0, 40)
                itemView.share.speed = 1.5f
                itemView.share.playAnimation()
                onQuestionShareIconClick.invoke(questionAnswer.question, questionAnswer.answer)
            }
        }
    }

    inner class BookmarksPrefixListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(prefix: Prefix, position: Int) {
            itemView.prefixTextView.text = HtmlCompat.fromHtml(prefix.text, HtmlCompat.FROM_HTML_MODE_COMPACT)

            itemView.prefixTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

            var isFavorite = prefix.isFavorite == 1

            if (isFavorite) {
                itemView.addToBookmarkPrefix.progress = 0.44f
            }
            itemView.copyPrefix.progress = 0.67f
            itemView.sharePrefix.progress = 0.67f

            itemView.addToBookmarkPrefix.setOnClickListener {
                if (!isFavorite) {
                    itemView.addToBookmarkPrefix.speed = 1f
                    itemView.addToBookmarkPrefix.playAnimation()
                } else {
                    itemView.addToBookmarkPrefix.speed = -1.7f
                    itemView.addToBookmarkPrefix.setMinAndMaxFrame(0, 22)
                    itemView.addToBookmarkPrefix.playAnimation()
                }
                isFavorite = !isFavorite
                onPrefixFavIconClick.invoke(prefix, position)
            }

            itemView.copyPrefix.setOnClickListener {
                itemView.copyPrefix.setMinAndMaxFrame(0, 80)
                itemView.copyPrefix.speed = 2.6f
                itemView.copyPrefix.playAnimation()
                onPrefixCopyIconClick.invoke(prefix.text)
            }

            itemView.sharePrefix.setOnClickListener {
                itemView.sharePrefix.setMinAndMaxFrame(0, 40)
                itemView.sharePrefix.speed = 1.5f
                itemView.sharePrefix.playAnimation()
                onPrefixShareIconClick.invoke(prefix.text)
            }
        }
    }
}