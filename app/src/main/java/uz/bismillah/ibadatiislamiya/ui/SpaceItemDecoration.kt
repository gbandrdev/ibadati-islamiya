package uz.bismillah.ibadatiislamiya.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(i: Int) : RecyclerView.ItemDecoration() {
    private var spaceHeight : Int = i

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spaceHeight
        outRect.right = spaceHeight
        outRect.top = spaceHeight
        outRect.bottom = spaceHeight
    }
}