package com.sample.utils

import android.content.Context
import android.view.View
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event

object Emitter {

    const val ITEM_PRESSED_HANDLED = "onPressHandle"

    class BaseEvent(
        surfaceId: Int,
        viewTag: Int,
        private val data: WritableMap,
        private val withName: String,
    ) : Event<BaseEvent>(surfaceId, viewTag) {
        override fun getEventName(): String = withName
        override fun getEventData(): WritableMap = data
    }

    private fun createEvent(
        event: WritableMap,
        name: String,
        viewTag: Int,
        surfaceId: Int
    ): BaseEvent {
        return BaseEvent(surfaceId, viewTag, event, name)
    }

    fun sendEvent(context: ReactContext, withName: String, withView: View, event: WritableMap) {
        val surfaceId = UIManagerHelper.getSurfaceId(withView)
        val dispatcher = UIManagerHelper.getEventDispatcher(context, withView.id)
        dispatcher?.dispatchEvent(createEvent(event, withName, withView.id, surfaceId))
    }
}