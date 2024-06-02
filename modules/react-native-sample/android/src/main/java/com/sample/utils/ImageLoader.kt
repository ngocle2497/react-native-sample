package com.sample.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.facebook.react.bridge.ReadableMap
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {
     fun loadImages(urls: ReadableMap, context: Context): HashMap<String, Bitmap?> {
        val resultMap = HashMap<String, Bitmap?>()
        runBlocking {
            val jobs = mutableListOf<Deferred<Pair<String, Bitmap?>>>()
            val iterator = urls.keySetIterator()
            while (iterator.hasNextKey()) {
                val key = iterator.nextKey()
                urls.getString(key)?.let {
                    val job = if (it.startsWith("http")) {
                        async {
                            val bitmap = loadImage(it)
                            key to bitmap
                        }
                    } else {
                        async {
                            val bitmap = loadLocalImage(context, it)
                            key to bitmap
                        }
                    }
                    jobs.add(job)

                }
            }

            val results = jobs.awaitAll()

            results.forEach { (key, bitmap) ->
                resultMap[key] = bitmap
            }
        }
        return resultMap
    }

    @SuppressLint("DiscouragedApi")
    private fun loadLocalImage(context: Context, resourceName: String): Bitmap? {
        return try {
            val resId =
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            BitmapFactory.decodeResource(context.resources, resId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun loadImage(url: String): Bitmap {
        return withContext(Dispatchers.IO) {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            return@withContext BitmapFactory.decodeStream(input).also {
                input.close()
                connection.disconnect()
            }
        }
    }
}