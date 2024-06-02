package com.sample.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReadableType
import com.facebook.react.common.assets.ReactFontManager
import com.facebook.react.common.assets.ReactFontManager.TypefaceStyle
import com.facebook.react.uimanager.PixelUtil
import com.sample.utils.ImageLoader
import com.sample.utils.Logg
import kotlin.math.ceil

enum class ColorName {
    attention,
    error,
    success,
    info,
    primary,
    background,
    backgroundSurfaces,
    textPrimary,
    textPrimaryBody,
    textSecondaryBody,
    border,
    warning;

    val color: Int
        get() {
            return Theming.instance?.getColor(this) ?: Color.TRANSPARENT
        }
}

enum class IconName {
    up,
    down;

    val icon: Bitmap?
        get() {
            return Theming.instance?.getIcon(this)
        }
}

enum class TextStyles {
    headlineLarge,
    headlineMedium,
    headlineSmall,

    titleLarge,
    titleMedium,
    titleSmall,

    labelLarge,
    labelMedium,
    labelSmall,

    bodyLarge,
    bodyMedium,
    bodySmall;


    val font: Theming.TextPreset?
        get() {
            return Theming.instance?.getTextPreset(this)
        }
}

class Theming(private val context: Context) {

    inner class TextPreset(
        val context: Context,
        private val fontName: String,
        private val fontSize: Float
    ) {
        val typeface: Typeface
            get() {
                return ReactFontManager.getInstance()
                    .getTypeface(fontName, TypefaceStyle.NORMAL, context.assets)
            }

        val size: Float
            get() {
                return ceil(PixelUtil.toPixelFromDIP(fontSize))
            }

        val sizeAdjustsFit: Float
            get() {
                return ceil(PixelUtil.toPixelFromSP(fontSize))
            }
    }


    private var colorMap: HashMap<String, Int> = hashMapOf()
    private var iconMap: HashMap<String, Bitmap?> = hashMapOf()
    private var textPresetMap: HashMap<String, TextPreset> = hashMapOf()

    // ===== Public fun ===== \\

    fun getTextPreset(name: TextStyles): TextPreset? {
        return textPresetMap[name.name]
    }

    fun getIcon(name: IconName): Bitmap? {
        return iconMap[name.name]
    }

    fun updateTheme(theme: ReadableMap) {
        theme.getMap("images")?.let {
            loadImage(it)
        }
        theme.getMap("colors")?.let {
            val iterator = it.keySetIterator()
            while (iterator.hasNextKey()) {
                val key = iterator.nextKey()
                val value = it.getDynamic(key)

                when (value.type) {
                    ReadableType.Number -> {
                        colorMap[key.toString()] = value.asInt()
                    }

                    else -> {
                        Logg.d(
                            "Theming->updateTheme",
                            "Only support number value, use processColor to get it"
                        )
                    }
                }
            }
        }
        theme.getMap("textPresets")?.let {
            val iterator = it.keySetIterator()
            while (iterator.hasNextKey()) {
                val key = iterator.nextKey()
                val value = it.getDynamic(key)

                when (value.type) {
                    ReadableType.Map -> {
                        val fontFamily = value.asMap().getString("fontFamily") ?: ""
                        val fontSize = value.asMap().getDouble("fontSize").toFloat()
                        textPresetMap[key.toString()] = TextPreset(context, fontFamily, fontSize)
                    }

                    else -> {
                        Logg.d("Theming->updateTheme", "Only support object type")
                    }
                }
            }
        }
    }

    fun getColor(name: ColorName): Int {
        colorMap[name.name]?.let {
            return it
        }
        return Color.TRANSPARENT
    }

    // ===== Private fun ===== \\
    private fun loadImage(image: ReadableMap) {
        val map = ImageLoader.loadImages(image, context)
        map.forEach { (key, bitmap) ->
            iconMap[key] = bitmap
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        var instance: Theming? = null

        fun createInstance(context: Context) {
            instance = Theming(context)
        }

    }
}