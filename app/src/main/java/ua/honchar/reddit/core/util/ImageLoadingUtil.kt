package ua.honchar.reddit.core.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.model.GlideUrl
import java.io.File

class ImageLoadingUtil(
    private val context: Context
) {

    fun loadImage(
        imagePath: String,
        imageView: ImageView,
    ) {
        Glide.with(imageView)
            .loadWithId(imagePath, getIdFromUrl(imagePath))
            .into(imageView)
    }

    fun loadImageWithPlaceholder(
        imagePath: String,
        imageView: ImageView,
        @DrawableRes placeholder: Int
    ) {
        Glide.with(imageView)
            .loadWithId(imagePath, getIdFromUrl(imagePath))
            .placeholder(placeholder)
            .into(imageView)
    }


    private fun getIdFromUrl(imagePath: String) =
        imagePath.split("/").last().substringBefore(".")

    private fun RequestManager.loadWithId(
        imagePath: String,
        id: String
    ) = asBitmap().loadWithId(imagePath, id)

    private fun <T> RequestBuilder<T>.loadWithId(
        imagePath: String,
        id: String
    ): RequestBuilder<T> {
        val urlWithoutParams = imagePath.substringBefore("?")
        val key = id.plus(urlWithoutParams)
        val file = File(imagePath)

        return if (file.exists()) {
            load(file)
        } else {
            load(imagePath.ifEmpty { null }?.let {
                GlideUrlWithCache(it, key)
            })
        }
    }
}

class GlideUrlWithCache(
    url: String,
    private val key: String
) : GlideUrl(url) {
    override fun getCacheKey(): String {
        return key
    }
}