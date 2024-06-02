package com.sample.viewmanagers

import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class FooterWrapperViewManager : ViewGroupManager<FooterWrapperView>() {
    override fun getName() = REACT_CLASS

    override fun createViewInstance(reactContext: ThemedReactContext): FooterWrapperView {
        return FooterWrapperView(reactContext)
    }

    companion object {
        const val REACT_CLASS = "FooterWrapperView"
    }
}
