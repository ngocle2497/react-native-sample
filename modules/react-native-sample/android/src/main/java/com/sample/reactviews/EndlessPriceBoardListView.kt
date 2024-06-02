package com.sample.reactviews

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.sample.adapter.EndlessPriceBoardAdapter
import com.sample.common.BaseListView
import com.sample.common.ViewHolderWrapper
import com.sample.entity.EndlessQuote
import com.sample.utils.Emitter
import com.sample.utils.Logg
import java.util.Timer
import java.util.TimerTask

class EndlessPriceBoardListView(context: Context) : BaseListView<EndlessQuote>(context) {
    private val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    private val adapter: EndlessPriceBoardAdapter = EndlessPriceBoardAdapter(listItems)

    private val timer: Timer = Timer()
    private var shouldPausedScroll = false
private var scrollState = RecyclerView.SCROLL_STATE_IDLE
    private val scrollListener = (object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            if (firstVisiblePosition < 1) {
                layoutManager.scrollToPositionWithOffset(adapter.itemCount - BUFFER_ITEMS, 0)
            } else if (lastVisiblePosition > (listItems.size - 1 + BUFFER_ITEMS)) {
                val view = layoutManager.findViewByPosition(lastVisiblePosition)
                val offset: Int = recyclerView.width - view!!.width
                layoutManager.scrollToPositionWithOffset(BUFFER_ITEMS - 1, offset)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Logg.d("ScrollState", "$newState")
            scrollState = newState
            shouldPausedScroll = newState != 0
        }
    })

    private val onTouchListener = (object : OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if(event?.action === MotionEvent.ACTION_UP && scrollState ==  RecyclerView.SCROLL_STATE_IDLE){
                shouldPausedScroll = false
            } else {
                shouldPausedScroll = true
            }
            return false
        }
    })

    init {
        recyclerView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        recyclerView.layoutManager = layoutManager
        layoutManager.isItemPrefetchEnabled = false
        recyclerView.setOnTouchListener(onTouchListener)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        addView(recyclerView)
    }


    companion object {
        val BUFFER_ITEMS = 5
        val MINIMUM_TO_LOOP = 8
    }

    override fun updateItem(index: Int, item: EndlessQuote, oldItem: EndlessQuote) {
        super.updateItem(index, item, oldItem)
        if (listItems.size >= MINIMUM_TO_LOOP) {
            val listIndexToUpdate = if (index == 0) {
                arrayListOf(0, 1)
            } else {
                arrayListOf(index + 1, listItems.size + index)
            }
            for (element in listIndexToUpdate) {
                if (oldItem.tradePriceStatus != item.tradePriceStatus) {
                    (recyclerView.findViewHolderForAdapterPosition(element) as? ViewHolderWrapper<EndlessQuote>)?.animatePrice(
                        item
                    )
                } else {
                    (recyclerView.findViewHolderForAdapterPosition(element) as? ViewHolderWrapper<EndlessQuote>)?.bindingData(
                        item
                    )
                }
            }
        } else {
            if (oldItem.tradePriceStatus != item.tradePriceStatus) {
                (recyclerView.findViewHolderForAdapterPosition(index + 1) as? ViewHolderWrapper<EndlessQuote>)?.animatePrice(
                    item
                )
            } else {
                (recyclerView.findViewHolderForAdapterPosition(index) as? ViewHolderWrapper<EndlessQuote>)?.bindingData(
                    item
                )
            }
        }

    }

    override fun setData(items: List<EndlessQuote>) {
        super.setData(items)
        if (items.size >= MINIMUM_TO_LOOP) {
            recyclerView.addOnScrollListener(scrollListener)
            layoutManager.scrollToPosition(1)
            startAutoScroll()
        } else {
            recyclerView.removeOnScrollListener(scrollListener)
            stopAutoScroll()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        shouldPausedScroll = true
        recyclerView.removeOnScrollListener(scrollListener)
        recyclerView.setOnTouchListener(null)
        timer.cancel()
    }

    // ===== Private fun ===== \\
    private fun startAutoScroll() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                recyclerView.post {
                    run {
                        if (!shouldPausedScroll) {
                            recyclerView.scrollBy(1, 0)
                        }
                    }
                }
            }
        }, 1000, 5)
    }

    private fun stopAutoScroll() {
        timer.cancel()
    }


    // ===== Public fun ===== \\
    fun sendPressEvent(keyItem: String) {
        val index = listItems.indexOfFirst { it.key == keyItem }
        if (index >= 0) {
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
