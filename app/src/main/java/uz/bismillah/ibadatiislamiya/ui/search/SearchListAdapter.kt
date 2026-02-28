package uz.bismillah.ibadatiislamiya.ui.search

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_search.view.*
import uz.bismillah.ibadatiislamiya.R

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {
    private var textSize = 18f

    var models = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setTextSize(size: Float) {
        textSize = size
        notifyItemRangeChanged(0, models.size)
    }

    private var onSearchingResultClick: (question: String) -> Unit = {}
    fun setOnSearchingResultClickListener(onSearchingResultClick: (question: String) -> Unit) {
        this.onSearchingResultClick = onSearchingResultClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size

    inner class SearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(question: String) {
            val questionRemovedStartTag = question.replace("<p>", " ")
            val questionFinal = questionRemovedStartTag.replace("</p>", "")
            itemView.searchQuestionTextView.text = questionFinal
            itemView.searchQuestionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

            itemView.setOnClickListener {
                onSearchingResultClick.invoke(question)
            }
        }
    }
}
