package com.sample.common

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sample.entity.BaseEntity
import com.sample.entity.ItemViewType

abstract class BaseAdapter<T>(private val listView: ArrayList<T>) :
    RecyclerView.Adapter<ViewHolder>() where T : BaseEntity {
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return listView.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = listView[position]
        return if (item.isHeader) {
            ItemViewType.HEADER.type
        } else if (item.isFooter) {
            ItemViewType.FOOTER.type
        } else {
            ItemViewType.ITEM.type
        }
    }
}