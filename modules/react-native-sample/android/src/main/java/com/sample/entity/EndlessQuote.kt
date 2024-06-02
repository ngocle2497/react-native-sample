package com.sample.entity

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

data class EndlessQuote(
    override val key: String,
    val symbolName: String,
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
            putString("tradePrice", tradePrice)
            putString("tradePriceStatus", tradePriceStatus.toString())
            putString("point", point)
            putString("pointStatus", pointStatus.toString())
            putString("percent", percent)
            putString("percentStatus", percentStatus.toString())
        }
    }

    companion object {
        fun fromArray(array: ReadableArray): ArrayList<EndlessQuote> {
            val resultArray = arrayListOf<EndlessQuote>()
            for (i in 0 until array.size()) {
                val map = array.getMap(i)
                resultArray.add(fromMap(map))
            }
            return resultArray
        }

        fun fromMap(map: ReadableMap): EndlessQuote {
            val key = map.getString("key") ?: ""
            val symbolName = map.getString("symbolName") ?: ""
            val tradePrice = map.getString("tradePrice") ?: ""
            val tradePriceStatus =
                QuoteBaseStatus.fromString(map.getString("tradePriceStatus") ?: "")
            val point = map.getString("point") ?: ""
            val pointStatus = QuoteBaseStatus.fromString(map.getString("pointStatus") ?: "")
            val percent = map.getString("percent") ?: ""
            val percentStatus = QuoteBaseStatus.fromString(map.getString("percentStatus") ?: "")
            val isFooter = false
            val isHeader = false

            return EndlessQuote(
                key = key,
                symbolName = symbolName,
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