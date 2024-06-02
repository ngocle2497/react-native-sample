package com.sample.viewmanagers

import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class HeaderWrapperViewManager : ViewGroupManager<HeaderWrapperView>() {
    override fun getName() = REACT_CLASS

    override fun createViewInstance(reactContext: ThemedReactContext): HeaderWrapperView {
        return HeaderWrapperView(reactContext)
    }

    companion object {
        const val REACT_CLASS = "HeaderWrapperView"
    }
}
