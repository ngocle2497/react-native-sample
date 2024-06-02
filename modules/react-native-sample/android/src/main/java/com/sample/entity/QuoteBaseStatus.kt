package com.sample.entity

enum class QuoteBaseStatus(val type: String) {
    UP("UP"),
    DOWN("DOWN"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun fromString(type: String): QuoteBaseStatus {
            return when (type) {
                "UP" -> UP
                "DOWN" -> DOWN
                else -> UNKNOWN
            }
        }
    }
}