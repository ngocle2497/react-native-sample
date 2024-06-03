package com.sample.viewmanagers

import android.view.View
import androidx.core.view.get
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.views.view.ReactViewGroup
import com.sample.reactviews.FooterWrapperView
import com.sample.reactviews.HeaderWrapperView
import com.sample.reactviews.PriceBoardListView
import com.sample.utils.Emitter

@ReactModule(name = PriceBoardListViewManager.REACT_CLASS)
class PriceBoardListViewManager : ViewGroupManager<PriceBoardListView>() {
    override fun getName() = REACT_CLASS

    override fun createViewInstance(reactContext: ThemedReactContext): PriceBoardListView {
        return PriceBoardListView(reactContext)
    }

    override fun addView(parent: PriceBoardListView?, child: View?, index: Int) {
        if (child is ReactViewGroup && child.childCount == 1 && child[0] is HeaderWrapperView) {
            parent?.addHeader(child[0] as HeaderWrapperView)
        }
        if (child is ReactViewGroup && child.childCount == 1 && child[0] is FooterWrapperView) {
            parent?.addFooter(child[0] as FooterWrapperView)
        }
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
