package me.simple.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaxLineLinearLayoutManager : LinearLayoutManager {

    private var mMaxLine = 0

    constructor(
        context: Context?,
        maxLine: Int
    ) : super(context) {
        Helper.checkMaxCount(maxLine)
        this.mMaxLine = maxLine
    }

    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean,
        maxLine: Int
    ) : super(context, orientation, reverseLayout) {
        Helper.checkMaxCount(maxLine)
        this.mMaxLine = maxLine
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        if (mMaxLine <= itemCount || itemCount == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
            return
        }

        val child = recycler.getViewForPosition(0)

        addView(child)
        measureChildWithMargins(child, 0, 0)

        val width = getDecoratedMeasuredWidth(child)
        val height = getDecoratedMeasuredHeight(child)

        if (orientation == HORIZONTAL) {
            setMeasuredDimension(width * mMaxLine, height)
        } else {
            setMeasuredDimension(width, height * mMaxLine)
        }

        removeAndRecycleView(child, recycler)
    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (mMaxLine <= itemCount) {
            return super.isAutoMeasureEnabled()
        }
        return false
    }


}