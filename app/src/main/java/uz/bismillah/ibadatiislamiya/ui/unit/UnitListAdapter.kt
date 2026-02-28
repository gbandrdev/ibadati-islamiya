package uz.bismillah.ibadatiislamiya.ui.unit

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_unit.view.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.model.Units

class UnitListAdapter : RecyclerView.Adapter<UnitListAdapter.UnitListViewHolder>() {

    var models = listOf<Units>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onUnitItemClick : (id: Int, title: String) -> Unit = { _, _ -> }

    fun setOnUnitItemClickListener(onUnitItemClick: (id: Int, title: String) -> Unit) {
        this.onUnitItemClick = onUnitItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_unit, parent, false)
        return UnitListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UnitListViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size

    inner class UnitListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(unit: Units) {
            itemView.unitTitleTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(unit.name, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(unit.name)
            }

            Glide
                .with(itemView)
                .load(itemView.context.resources.getIdentifier(unit.image, "drawable", itemView.context.packageName))
                .into(itemView.unitIcon)


            itemView.setOnClickListener {
                onUnitItemClick.invoke(unit.id, unit.name)
            }
        }
    }
}