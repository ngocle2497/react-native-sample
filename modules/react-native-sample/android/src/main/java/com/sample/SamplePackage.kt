package com.sample

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.sample.module.SampleModule
import com.sample.viewmanagers.EndlessPriceBoardListViewManager
import com.sample.viewmanagers.FooterWrapperViewManager
import com.sample.viewmanagers.HeaderWrapperViewManager
import com.sample.viewmanagers.PriceBoardListViewManager

class SamplePackage : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
    return listOf(SampleModule(reactContext))
  }

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(
        EndlessPriceBoardListViewManager(),
        PriceBoardListViewManager(),
        FooterWrapperViewManager(),
        HeaderWrapperViewManager()
    )
  }
}
