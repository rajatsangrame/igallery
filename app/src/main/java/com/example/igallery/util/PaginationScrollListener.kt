package com.example.igallery.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

abstract class PaginationScrollListener(private val layoutManager: LayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = when (layoutManager) {
            is GridLayoutManager -> {
                layoutManager.findFirstVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                layoutManager.findFirstVisibleItemPosition()
            }
            else -> {
                throw java.lang.RuntimeException("LayoutManager not supported")
            }
        }

        if (!isLoading() && visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0
        ) {
            loadMoreItems()
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLoading(): Boolean
}
