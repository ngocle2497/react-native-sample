package com.sample.entity

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

data class Quote(
    override val key: String,
    val symbolName: String,
    val companyName: String,
    val tradePrice: String,
    val tradePriceStatus: QuoteBaseStatus,
    val point: String,
    val pointStatus: QuoteBaseStatus,
    val percent: String,
    val percentStatus: QuoteBaseStatus,
    override val isFooter: Boolean,
    override val isHeader: Boolean
) : BaseEntity {

    fun toMap(): WritableMap {
        return Arguments.createMap().apply {
            putString("key", key)
            putString("symbolName", symbolName)
            putString("companyName", companyName)
            putString("tradePrice", tradePrice)
            putString("tradePriceStatus", tradePriceStatus.toString())
            putString("point", point)
            putString("pointStatus", pointStatus.toString())
            putString("percent", percent)
            putString("percentStatus", percentStatus.toString())
        }
    }

    companion object {
        fun createHeaderFooter(isHeader: Boolean): Quote {
            return Quote(
                key = if (isHeader) {
                    "HEADER"
                } else {
                    "FOOTER"
                },
                symbolName = "",
                companyName = "",
                tradePrice = "",
                tradePriceStatus = QuoteBaseStatus.UNKNOWN,
                point = "",
                pointStatus = QuoteBaseStatus.UNKNOWN,
                percent = "",
                percentStatus = QuoteBaseStatus.UNKNOWN,
                isFooter = !isHeader,
                isHeader = isHeader
            )
        }

        fun fromArray(array: ReadableArray): ArrayList<Quote> {
            val resultArray = arrayListOf<Quote>()
            for (i in 0 until array.size()) {
                val map = array.getMap(i)
                resultArray.add(fromMap(map))
            }
            return resultArray
        }

        fun fromMap(map: ReadableMap): Quote {
            val key = map.getString("key") ?: ""
            val symbolName = map.getString("symbolName") ?: ""
            val companyName = map.getString("companyName") ?: ""
            val tradePrice = map.getString("tradePrice") ?: ""
            val tradePriceStatus =
                QuoteBaseStatus.fromString(map.getString("tradePriceStatus") ?: "")
            val point = map.getString("point") ?: ""
            val pointStatus = QuoteBaseStatus.fromString(map.getString("pointStatus") ?: "")
            val percent = map.getString("percent") ?: ""
            val percentStatus = QuoteBaseStatus.fromString(map.getString("percentStatus") ?: "")
            val isFooter = false
            val isHeader = false

            return Quote(
                key = key,
                symbolName = symbolName,
                companyName = companyName,
                tradePrice = tradePrice,
                tradePriceStatus = tradePriceStatus,
                point = point,
                pointStatus = pointStatus,
                percent = percent,
                percentStatus = percentStatus,
                isFooter = isFooter,
                isHeader = isHeader
            )
        }
    }
}
