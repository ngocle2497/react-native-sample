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
        val header: HeaderWrapperView? = null
        val footer: FooterWrapperView? = null
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
