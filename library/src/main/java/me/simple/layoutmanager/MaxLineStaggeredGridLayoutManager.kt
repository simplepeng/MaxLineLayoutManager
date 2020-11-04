package me.simple.layoutmanager

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MaxLineStaggeredGridLayoutManager(
    spanCount: Int,
    orientation: Int,
    maxLine: Int
) : StaggeredGridLayoutManager(spanCount, orientation) {

    private var mMaxLine = maxLine

    init {
        Helper.checkMaxCount(maxLine)
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        if (itemCount <= mMaxLine * spanCount || itemCount == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
            return
        }

        var maxItemWidth = 0
        var maxItemHeight = 0

        val endIndex = if (itemCount < mMaxLine * spanCount) {
            itemCount
        } else {
            mMaxLine * spanCount
        }
        for (index in 0 until endIndex) {
            val child = recycler.getViewForPosition(index)
            addView(child)
            measureChildWithMargins(child, 0, 0)

            val itemWidth = getDecoratedMeasuredWidth(child)
            val itemHeight = getDecoratedMeasuredHeight(child)

            removeAndRecycleView(child, recycler)

            if (itemWidth > maxItemWidth) {
                maxItemWidth = itemWidth
            }
            if (itemHeight > maxItemHeight) {
                maxItemHeight = itemHeight
            }
        }

        if (orientation == HORIZONTAL) {
            setMeasuredDimension(maxItemWidth * mMaxLine, maxItemHeight * spanCount)
        } else {
            setMeasuredDimension(maxItemWidth * spanCount, maxItemHeight * mMaxLine)
        }
    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (itemCount <= mMaxLine * spanCount) {
            return super.isAutoMeasureEnabled()
        }

        return false
    }

}