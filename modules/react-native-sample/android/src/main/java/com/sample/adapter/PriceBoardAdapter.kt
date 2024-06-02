package com.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sample.R
import com.sample.common.BaseAdapter
import com.sample.common.ViewHolderWrapper
import com.sample.entity.ItemViewType
import com.sample.entity.Quote
import com.sample.reactviews.PriceBoardListView
import com.sample.viewholders.HeaderFooterWrapperViewHolder
import com.sample.viewholders.ItemPriceBoardViewHolder

class PriceBoardAdapter(private val list: ArrayList<Quote>) : BaseAdapter<Quote>(list) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            ItemViewType.FOOTER.type -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.footer_wrapper, parent, false)
                return HeaderFooterWrapperViewHolder(
                    view,
                    PriceBoardListView.footer,
                    R.id.lnFooterWrapper
                )
            }

            ItemViewType.HEADER.type -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.header_wrapper, parent, false)
                return HeaderFooterWrapperViewHolder(
                    view,
                    PriceBoardListView.header,
                    R.id.lnHeaderWrapper
                )
            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_price_board, parent, false)
                return ItemPriceBoardViewHolder(view, recyclerView )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ViewHolderWrapper<*>) {
            (holder as ViewHolderWrapper<Quote>).bindingData(list[position])
        }
    }
}
