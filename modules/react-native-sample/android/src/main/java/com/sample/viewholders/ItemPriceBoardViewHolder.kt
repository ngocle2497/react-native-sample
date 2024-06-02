package com.sample.viewholders

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
import com.sample.R
import com.sample.common.ColorName
import com.sample.common.IconName
import com.sample.common.TextStyles
import com.sample.common.ViewHolderWrapper
import com.sample.entity.Quote
import com.sample.entity.QuoteBaseStatus
import com.sample.reactviews.PriceBoardListView


class ItemPriceBoardViewHolder(itemView: View, val recyclerView: RecyclerView?) :
    ViewHolderWrapper<Quote>(itemView) {
    private val tvSymbolName: TextView = itemView.findViewById(R.id.tvSymbolName)
    private val tvCompanyName: TextView = itemView.findViewById(R.id.tvCompanyName)
    private val tvTradePrice: TextView = itemView.findViewById(R.id.tvTradePrice)
    private val tvPoint: TextView = itemView.findViewById(R.id.tvPoint)
    private val tvPercent: TextView = itemView.findViewById(R.id.tvPercent)
    private val cvContainer: CardView = itemView.findViewById(R.id.cvContainer)
    private val cvContainerPrice: CardView = itemView.findViewById(R.id.cvContainerPrice)
    private val imgUpDownIcon: ImageView = itemView.findViewById(R.id.imgUpDownIcon)
    private val lnIconWrapper: LinearLayout = itemView.findViewById(R.id.lnIconWrapper)

    init {
        setupView()
        setupEvent()
    }

    override fun bindingData(data: Quote) {
        tvSymbolName.text = data.symbolName
        tvCompanyName.text = data.companyName
        tvPoint.text = data.point
        tvPercent.text = data.percent
        tvTradePrice.text = data.tradePrice

        val tintPointColor = when (data.pointStatus) {
            QuoteBaseStatus.UP -> ColorName.success.color
            QuoteBaseStatus.DOWN -> ColorName.error.color
            else -> ColorName.textPrimary.color
        }
        val icon = when (data.pointStatus) {
            QuoteBaseStatus.UP -> IconName.up.icon
            QuoteBaseStatus.DOWN -> IconName.down.icon
            else -> null
        }
        if (icon == null) {
            UiThreadUtil.runOnUiThread {
                run {
                    lnIconWrapper.visibility = LinearLayout.GONE
                }
            }
        } else {
            UiThreadUtil.runOnUiThread {
                run {
                    lnIconWrapper.visibility = LinearLayout.VISIBLE
                }
            }
            imgUpDownIcon.setImageBitmap(icon)
            imgUpDownIcon.setColorFilter(tintPointColor)
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

    override fun animatePrice(data: Quote) {
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
        cvContainer.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (recyclerView?.parent is PriceBoardListView) {
                    (recyclerView.parent as  PriceBoardListView).sendPressEvent(absoluteAdapterPosition)
                }
            }
        })
    }

    private fun setupView() {
        cvContainer.setCardBackgroundColor(ColorName.backgroundSurfaces.color)
        tvSymbolName.setTextColor(ColorName.textPrimary.color)
        TextStyles.titleMedium.font?.let {
            tvSymbolName.typeface = it.typeface
            tvSymbolName.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.size)

        }
        tvCompanyName.setTextColor(ColorName.textPrimary.color)
        TextStyles.labelLarge.font?.let {
            tvTradePrice.typeface = it.typeface
            tvCompanyName.typeface = it.typeface
            tvCompanyName.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.size)
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