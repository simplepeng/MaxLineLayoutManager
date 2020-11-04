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
        if (itemCount < mMaxLine || itemCount == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
            return
        }

        val child = recycler.getViewForPosition(0)

        addView(child)
        measureChildWithMargins(child, 0, 0)
        val itemWidth = getDecoratedMeasuredWidth(child)
        val itemHeight = getDecoratedMeasuredHeight(child)
        removeAndRecycleView(child, recycler)

        if (orientation == HORIZONTAL) {
            setMeasuredDimension(itemWidth * mMaxLine, itemHeight)
        } else {
            setMeasuredDimension(itemWidth, itemHeight * mMaxLine)
        }


    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (itemCount < mMaxLine) {
            return super.isAutoMeasureEnabled()
        }
        return false
    }


}