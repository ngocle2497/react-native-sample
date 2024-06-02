package com.sample.viewmanagers

import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

@ReactModule(name = PriceBoardListViewManager.REACT_CLASS)
class PriceBoardListViewManager : ViewGroupManager<PriceBoardListView>() {
  override fun getName() = REACT_CLASS

  override fun createViewInstance(reactContext: ThemedReactContext): PriceBoardListView {
    return PriceBoardListView(reactContext)
  }
  companion object {
    const val REACT_CLASS = "PriceBoardListView"
  }
}
