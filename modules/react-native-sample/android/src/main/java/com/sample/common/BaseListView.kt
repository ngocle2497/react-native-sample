package com.sample.common

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import com.facebook.react.bridge.UiThreadUtil
import com.sample.entity.BaseEntity

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
        header?.let {
            listItems.add(0, header)
        }
        footer?.let {
            listItems.add(listItems.lastIndex + 1, footer)
        }
        listItems.addAll(items)
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

}
