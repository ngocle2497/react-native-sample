package com.sample.viewholders

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
import com.google.android.material.card.MaterialCardView
import com.sample.R
import com.sample.adapter.EndlessPriceBoardAdapter
import com.sample.common.ColorName
import com.sample.common.TextStyles
import com.sample.common.ViewHolderWrapper
import com.sample.entity.EndlessQuote
import com.sample.entity.QuoteBaseStatus
import com.sample.reactviews.EndlessPriceBoardListView
import com.sample.reactviews.PriceBoardListView

class ItemEndlessPriceBoardViewHolder(itemView: View, val recyclerView: RecyclerView?) :
    ViewHolderWrapper<EndlessQuote>(itemView) {
    private val tvSymbolName: TextView = itemView.findViewById(R.id.tvSymbolName)
    private val tvTradePrice: TextView = itemView.findViewById(R.id.tvTradePrice)
    private val tvPoint: TextView = itemView.findViewById(R.id.tvPoint)
    private val tvPercent: TextView = itemView.findViewById(R.id.tvPercent)
    private val cvContainer: MaterialCardView = itemView.findViewById(R.id.cvContainer)
    private val cvContainerPrice: CardView = itemView.findViewById(R.id.cvContainerPrice)
    private var keyItem: String = ""

    init {
        setupView()
        setupEvent()
    }

    // ===== Public fun ===== \\
    override fun bindingData(data: EndlessQuote) {
        keyItem = data.key
        cvContainerPrice.setCardBackgroundColor(Color.TRANSPARENT)
        tvSymbolName.text = data.symbolName
        tvPoint.text = data.point
        tvPercent.text = data.percent
        tvTradePrice.text = data.tradePrice
        val tintPointColor = when (data.pointStatus) {
            QuoteBaseStatus.UP -> ColorName.success.color
            QuoteBaseStatus.DOWN -> ColorName.error.color
            else -> ColorName.textPrimary.color
        }

        tvTradePrice.setTextColor(
            when (data.tradePriceStatus) {
                QuoteBaseStatus.UP -> ColorName.success.color
                QuoteBaseStatus.DOWN -> ColorName.error.color
                else -> ColorName.textPrimary.color
            }
        )
        tvPoint.setTextColor(tintPointColor)
        tvPercent.setTextColor(
            when (data.percentStatus) {
                QuoteBaseStatus.UP -> ColorName.success.color
                QuoteBaseStatus.DOWN -> ColorName.error.color
                else -> ColorName.textPrimary.color
            }
        )
    }

    override fun animatePrice(data: EndlessQuote) {
        super.animatePrice(data)
        if (data.tradePriceStatus != QuoteBaseStatus.UNKNOWN) {
            val tintColor = if (data.tradePriceStatus == QuoteBaseStatus.UP) {
                ColorName.success.color
            } else {
                ColorName.error.color
            }

            UiThreadUtil.runOnUiThread {
                run {
                    val priceColorAnimation =
                        ValueAnimator.ofObject(
                            ArgbEvaluator(),
                            ColorName.textPrimary.color,
                            tintColor
                        )
                    priceColorAnimation.duration = 700
                    priceColorAnimation.addUpdateListener { animator ->
                        tvTradePrice.setTextColor(
                            animator.animatedValue as Int
                        )
                    }
                    priceColorAnimation.start()

                    val overlayColorAnimation =
                        ValueAnimator.ofObject(ArgbEvaluator(), tintColor, Color.TRANSPARENT)
                    overlayColorAnimation.duration = 700
                    overlayColorAnimation.addUpdateListener { animator ->
                        cvContainerPrice.setCardBackgroundColor(
                            animator.animatedValue as Int
                        )
                    }
                    overlayColorAnimation.start()
                }
            }
        } else {
            cvContainerPrice.setCardBackgroundColor(Color.TRANSPARENT)
        }
    }

    // ===== Private fun ===== \\
    private fun setupEvent() {
        cvContainer.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (recyclerView?.parent is EndlessPriceBoardListView) {
                    (recyclerView.parent as EndlessPriceBoardListView).sendPressEvent(
                        keyItem
                    )
                }
            }
        })
    }

    private fun setupView() {
        cvContainerPrice.setCardBackgroundColor(Color.TRANSPARENT)
        cvContainer.setStrokeColor(ColorName.border.color)
        cvContainer.setCardBackgroundColor(ColorName.backgroundSurfaces.color)
        tvSymbolName.setTextColor(ColorName.textPrimary.color)
        TextStyles.titleMedium.font?.let {
            tvSymbolName.typeface = it.typeface
            tvSymbolName.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.size)

        }
        TextStyles.labelLarge.font?.let {
            tvTradePrice.typeface = it.typeface
            tvTradePrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.size)
        }
        TextStyles.titleSmall.font?.let {
            tvPoint.typeface = it.typeface
            tvPercent.typeface = it.typeface
            tvPoint.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.sizeAdjustsFit)
            tvPercent.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.sizeAdjustsFit)
        }
    }
}