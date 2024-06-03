package com.sample.common

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import com.facebook.react.bridge.UiThreadUtil
import com.sample.entity.BaseEntity
import com.sample.reactviews.FooterWrapperView
import com.sample.reactviews.HeaderWrapperView

open class BaseListView<T>(context: Context) : FrameLayout(context) where T : BaseEntity {
    var recyclerView = PatchedRecyclerView(context)
    var listItems: ArrayList<T> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataChanged() {
        recyclerView.post {
            run {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun notifyItemChanged(index: Int) {
        UiThreadUtil.runOnUiThread {
            recyclerView.adapter?.notifyItemChanged(index)
        }
    }

    open fun setData(items: List<T>) {
        val header = listItems.firstOrNull { it.isHeader }
        val footer = listItems.firstOrNull { it.isFooter }
        listItems.clear()
        listItems.addAll(items)
        header?.let {
            listItems.add(0, header)
        }
        footer?.let {
            listItems.add(listItems.lastIndex + 1, footer)
        }
        notifyDataChanged()
    }

    fun updateItem(item: T) {
        val index = listItems.indexOfFirst { it.key == item.key }
        if (index >= 0) {
            val oldItem = listItems[index]
            listItems[index] = item
            updateItem(index, item, oldItem)
        }
    }

    open fun updateItem(index: Int, item: T, oldItem: T) {}

    open fun updateLayoutHeader(width: Int, height: Int) {

    }

    open fun updateLayoutFooter(width: Int, height: Int) {

    }

    open fun addHeader(header: HeaderWrapperView) {

    }

    open fun addFooter(footer: FooterWrapperView) {

    }
}
