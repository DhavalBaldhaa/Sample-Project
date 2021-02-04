package com.devstree.basesample.utilities.glide

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.devstree.basesample.R
import jp.wasabeef.glide.transformations.BlurTransformation

object GlideUtil {
    @JvmStatic
    var defaultRequestOption: RequestOptions? = null
        get() = if (field == null) {
            BuildRequestOptionRounded(R.drawable.ic_user_black).also { field = it }
        } else field
        private set

    fun getDefaultRequestOption(res_id: Int): RequestOptions {
        return BuildRequestOptionRounded(res_id)
    }

    fun BuildRequestOptionNormal(image_holder_portrait: Int): RequestOptions {
        return RequestOptions().placeholder(image_holder_portrait)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(image_holder_portrait)
    }

    fun BuildRequestOptionRounded(image_holder_portrait: Int): RequestOptions {
        return RequestOptions().placeholder(image_holder_portrait)
            .error(image_holder_portrait)
            .fallback(image_holder_portrait)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun BuildImageLoader(image_holder_portrait: Int): RequestOptions {
        return RequestOptions().placeholder(image_holder_portrait)
            .error(image_holder_portrait)
            .fallback(image_holder_portrait)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun BuildRequestOptionRounded(): RequestOptions {
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun BuildRequestVideoCenterCrop(image_holder_portrait: Int): RequestOptions {
        return RequestOptions().placeholder(image_holder_portrait).centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(image_holder_portrait)
    }

    fun BlurRequest(): BaseRequestOptions<*> {
        return RequestOptions.bitmapTransform(BlurTransformation(25, 3))
    }
}