package com.sample.module

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.UiThreadUtil
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.common.UIManagerType
import com.sample.common.ListViewType
import com.sample.common.Theming
import com.sample.entity.EndlessQuote
import com.sample.entity.Quote
import com.sample.reactviews.EndlessPriceBoardListView
import com.sample.reactviews.FooterWrapperView
import com.sample.reactviews.HeaderWrapperView
import com.sample.reactviews.PriceBoardListView
import com.sample.utils.Logg
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import java.util.concurrent.Executors
import kotlin.coroutines.resume

class SampleModule(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {

    override fun getName(): String = CLASS_NAME

    private val backgroundCoroutineScope =
        CoroutineScope(Executors.newCachedThreadPool().asCoroutineDispatcher())

    init {
        Theming.createInstance(context)
    }

    // ===== React fun ===== \\

    @ReactMethod
    fun setDataList(viewId: Double, type: String, data: ReadableArray) {
        backgroundCoroutineScope.launch {
            when (type) {
                ListViewType.PRICE_BOARD -> {

                    val view = findView<PriceBoardListView>(viewId.toInt())
                    view?.setData(Quote.fromArray(data))
                }
                ListViewType.ENDLESS -> {
                    val view = findView<EndlessPriceBoardListView>(viewId.toInt())
                    view?.setData(EndlessQuote.fromArray(data))
                }
                else -> {}
            }
        }
    }

    @ReactMethod
    fun setTheme(data: ReadableMap, promise: Promise) {
        Theming.instance?.updateTheme(data)
        promise.resolve(true)
    }

    @ReactMethod
    fun updateItemList(viewId: Double, type: String, data: ReadableMap) {
        backgroundCoroutineScope.launch {
            when (type) {
                ListViewType.PRICE_BOARD -> {
                    val view = findView<PriceBoardListView>(viewId.toInt())
                    view?.updateItem(Quote.fromMap(data))
                }
                ListViewType.ENDLESS -> {
                    val view = findView<EndlessPriceBoardListView>(viewId.toInt())
                    view?.updateItem(EndlessQuote.fromMap(data))
                }
                else -> {}
            }
        }
    }

    @ReactMethod
    fun updateHeaderList(viewId: Double, headerId: Double) {
        backgroundCoroutineScope.launch(Dispatchers.Main) {
            val view = findView<PriceBoardListView>(
                viewId.toInt(),
            )
            val headerView = findView<HeaderWrapperView>(
                headerId.toInt(),
            )
            view?.updateLayoutHeader(
                headerView?.width ?: 0,
                headerView?.height ?: 0
            )
        }
    }

    @ReactMethod
    fun updateFooterList(viewId: Double, footerId: Double) {
        backgroundCoroutineScope.launch(Dispatchers.Main) {
            val view = findView<PriceBoardListView>(
                viewId.toInt(),
            )
            val footerView = findView<FooterWrapperView>(
                footerId.toInt(),
            )
            view?.updateLayoutFooter(
                footerView?.width ?: 0,
                footerView?.height ?: 0
            )
        }
    }

    // ===== Private fun ===== \\
    private suspend fun <T> findView(viewId: Int): T? =
        runOnUiThreadAndWait {
            Logg.d(TAG, "Finding view $viewId...")
            val context = reactApplicationContext ?: throw Error("React Context was null!")

            val uiManager = UIManagerHelper.getUIManager(context, UIManagerType.DEFAULT)
                ?: throw Error("UIManager not found!")

            try {
                val view =
                    uiManager.resolveView(viewId) as? T
                Logg.d(TAG, "Found view $viewId!")
                return@runOnUiThreadAndWait view
            } catch (ex: Exception) {
                ex.printStackTrace()
                return@runOnUiThreadAndWait null
            }

        }

    private suspend inline fun <T> runOnUiThreadAndWait(crossinline function: () -> T): T {
        if (UiThreadUtil.isOnUiThread()) {
            // We are already on UI Thread - immediately call the function
            return function()
        }

        return suspendCancellableCoroutine { continuation ->
            UiThreadUtil.runOnUiThread {
                if (continuation.isCancelled) throw CancellationException()
                val result = function()
                continuation.resume(result)
            }
        }
    }

    companion object {
        const val CLASS_NAME = "SampleModule"
        const val TAG = "SAMPLE_MODULE"
    }
}