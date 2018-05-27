package org.firespeed.phpconfukuoka18.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView


class ScrollLayoutManager(context: Context?) : LinearLayoutManager(context) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        startSmoothScroll(object : LinearSmoothScroller(recyclerView.context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }.apply {
            targetPosition = position
        }
        )
    }
}