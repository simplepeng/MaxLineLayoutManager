package me.simple.layoutmanager

import android.content.Context
import android.view.View
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
        if (itemCount <= mMaxLine || itemCount == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
            return
        }

        val child = recycler.getViewForPosition(0)

        addView(child)
        measureChildWithMargins(child, 0, 0)
        val itemWidth = getDecoratedMeasuredWidth(child)
        val itemHeight = getDecoratedMeasuredHeight(child)
        removeAndRecycleView(child, recycler)

        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        var width = 0
        var height = 0

        if (orientation == HORIZONTAL) {
            height = if (heightMode == View.MeasureSpec.EXACTLY) {
                View.MeasureSpec.getSize(heightSpec)
            } else {
                itemHeight
            }
            width = itemWidth * mMaxLine
        } else {
            width = if (widthMode == View.MeasureSpec.EXACTLY) {
                View.MeasureSpec.getSize(widthSpec)
            } else {
                itemWidth
            }
            height = itemHeight * mMaxLine
        }

        setMeasuredDimension(width, height)
    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (itemCount <= mMaxLine) {
            return super.isAutoMeasureEnabled()
        }
        return false
    }


}