package me.simple.layoutmanager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaxLineGridLayoutManager : GridLayoutManager {

    private var mMaxLine = 0

    constructor(
        context: Context?,
        spanCount: Int,
        maxLine: Int
    ) : super(context, spanCount) {
        Helper.checkMaxCount(maxLine)
        this.mMaxLine = maxLine
    }

    constructor(
        context: Context?,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean,
        maxLine: Int
    ) : super(context, spanCount, orientation, reverseLayout) {
        Helper.checkMaxCount(maxLine)
        this.mMaxLine = maxLine
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        if (calculateItemLine() < mMaxLine || itemCount == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
            return
        }

        val child = recycler.getViewForPosition(0)
        addView(child)
        measureChildWithMargins(child, 0, 0)

        val width = getDecoratedMeasuredWidth(child)
        val height = getDecoratedMeasuredHeight(child)

        if (orientation == HORIZONTAL) {

        } else {
            setMeasuredDimension(width * spanCount, height * mMaxLine)
        }
    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (calculateItemLine() < mMaxLine) {
            return super.isAutoMeasureEnabled()
        }

        return false
    }

    /**
     *
     */
    private fun calculateItemLine() = itemCount / spanCount
}