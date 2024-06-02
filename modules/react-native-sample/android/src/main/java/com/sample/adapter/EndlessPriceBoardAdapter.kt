package com.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.R
import com.sample.common.BaseAdapter
import com.sample.common.ViewHolderWrapper
import com.sample.entity.EndlessQuote
import com.sample.reactviews.EndlessPriceBoardListView
import com.sample.viewholders.ItemEndlessPriceBoardViewHolder

class EndlessPriceBoardAdapter(private val list: ArrayList<EndlessQuote>) : BaseAdapter<EndlessQuote>(list) {
    override fun getItemCount(): Int {
        if (list.size >= EndlessPriceBoardListView.MINIMUM_TO_LOOP) {
            return list.size + EndlessPriceBoardListView.BUFFER_ITEMS + 1
        }
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_endless_price_board, parent, false)
        return ItemEndlessPriceBoardViewHolder(view, recyclerView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val actualIndex = if (list.size >= EndlessPriceBoardListView.MINIMUM_TO_LOOP) {
            if (position == 0 || position == 1) {
                0
            } else {
                (position - 1) % list.size
            }
        } else {
            position
        }

        if (holder is ViewHolderWrapper<*>) {
            (holder as ViewHolderWrapper<EndlessQuote>).bindingData(list[actualIndex])
        }
    }
}
