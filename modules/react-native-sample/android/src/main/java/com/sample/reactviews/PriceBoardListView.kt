package com.sample.reactviews

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.sample.adapter.PriceBoardAdapter
import com.sample.common.BaseListView
import com.sample.common.ViewHolderWrapper
import com.sample.entity.Quote
import com.sample.utils.Emitter
import com.sample.viewholders.HeaderFooterWrapperViewHolder

class PriceBoardListView(context: Context) : BaseListView<Quote>(context) {
    private val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    private val adapter: PriceBoardAdapter = PriceBoardAdapter(listItems)

    init {
        recyclerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        addView(recyclerView)
    }


    companion object {
        var headerView: HeaderWrapperView? = null
        var footerView: FooterWrapperView? = null
    }

    override fun updateItem(index: Int, item: Quote, oldItem: Quote) {
        super.updateItem(index, item, oldItem)
        if (oldItem.tradePriceStatus != item.tradePriceStatus) {
            (recyclerView.findViewHolderForAdapterPosition(index) as? ViewHolderWrapper<Quote>)?.animatePrice(
                item
            )
        } else {
            (recyclerView.findViewHolderForAdapterPosition(index) as? ViewHolderWrapper<Quote>)?.bindingData(
                item
            )
        }
    }
    override fun addHeader(header: HeaderWrapperView) {
        super.addHeader(header)
        headerView = header
        if ((listItems.size > 0 && listItems[0].isHeader != true) || listItems.isEmpty()) {
            listItems.add(0, Quote.createHeaderFooter(true))
        }

        val index = listItems.indexOfFirst { it.isHeader == true }
        adapter.notifyItemChanged(index, Unit)
    }

    override fun updateLayoutHeader(width: Int, height: Int) {
        val index = listItems.indexOfFirst { it.isHeader == true }
        val needUpdate =
            (recyclerView.findViewHolderForAdapterPosition(index) as? HeaderFooterWrapperViewHolder)?.updateLayout(
                height,
                headerView
            ) ?: false
        if (needUpdate) {
            adapter.notifyItemChanged(index, listItems[index])
        }
    }

    override fun addFooter(footer: FooterWrapperView) {
        super.addFooter(footer)
        footerView = footer
        if ((listItems.size > 0 && listItems[listItems.lastIndex].isFooter != true) || listItems.isEmpty()) {
            listItems.add(listItems.lastIndex + 1, Quote.createHeaderFooter(false))
        }

        val index = listItems.indexOfFirst { it.isFooter == true }
        adapter.notifyItemChanged(index, Unit)
    }

    override fun updateLayoutFooter(width: Int, height: Int) {
        val index = listItems.indexOfFirst { it.isFooter == true }
        val needUpdate =
            (recyclerView.findViewHolderForAdapterPosition(index) as? HeaderFooterWrapperViewHolder)?.updateLayout(
                height,
                footerView
            ) ?: false
        if (needUpdate) {
            adapter.notifyItemChanged(index, listItems[index])
        }
    }

    // ===== Public fun ===== \\
    fun sendPressEvent(index: Int) {
        if (index >= 0 && index < (listItems.size - 1)) {
            Emitter.sendEvent(
                recyclerView.context as ReactContext,
                Emitter.ITEM_PRESSED_HANDLED,
                this,
                Arguments.createMap().apply {
                    putMap("item", listItems[index].toMap())
                })
        }
    }
}
