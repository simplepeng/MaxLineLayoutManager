package me.simple.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaxCountLinearLayoutManager : LinearLayoutManager {

    constructor(
        context: Context?,
        maxCount: Int
    ) : super(context) {
        checkMaxCount(maxCount)
        this.maxCount = maxCount
    }

    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean,
        maxCount: Int
    ) : super(context, orientation, reverseLayout) {
        checkMaxCount(maxCount)
        this.maxCount = maxCount
    }

    private var maxCount = 0

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        var width = 0
        var height = 0
        if (itemCount > this.maxCount) {
            val child = recycler.getViewForPosition(0)

            addView(child)
            measureChildWithMargins(child, 0, 0)

            width = getDecoratedMeasuredWidth(child)
            height = getDecoratedMeasuredHeight(child)

            if (orientation == LinearLayoutManager.HORIZONTAL) {
                setMeasuredDimension(width * maxCount, height)
            } else {
                setMeasuredDimension(width, height * maxCount)
            }

            removeAndRecycleView(child, recycler)
            return
        }

        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun isAutoMeasureEnabled(): Boolean {
        if (itemCount > maxCount) {
            return false
        }
        return super.isAutoMeasureEnabled()
    }

    private fun checkMaxCount(maxCount: Int) {
        if (maxCount < 1) {
            throw IllegalArgumentException("maxCount = $maxCount must > 1")
        }
    }
}