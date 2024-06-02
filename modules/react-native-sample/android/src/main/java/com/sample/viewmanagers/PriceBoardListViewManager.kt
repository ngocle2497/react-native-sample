package com.sample.viewmanagers

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.sample.reactviews.PriceBoardListView
import com.sample.utils.Emitter

@ReactModule(name = PriceBoardListViewManager.REACT_CLASS)
class PriceBoardListViewManager : ViewGroupManager<PriceBoardListView>() {
    override fun getName() = REACT_CLASS

    override fun createViewInstance(reactContext: ThemedReactContext): PriceBoardListView {
        return PriceBoardListView(reactContext)
    }

    override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> {
        return mapOf(
            Emitter.ITEM_PRESSED_HANDLED to mapOf(
                "phasedRegistrationNames" to mapOf(
                    "bubbled" to Emitter.ITEM_PRESSED_HANDLED
                )
            )
        )
    }

    companion object {
        const val REACT_CLASS = "PriceBoardListView"
    }
}
