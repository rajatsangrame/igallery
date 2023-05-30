package com.example.igallery.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

fun ImageView.loadThumbnail(path: String, roundCorner: Boolean = false) {
    val file = File(path)
    var builder = Glide.with(this.context).load(file)
    builder = if (roundCorner) {
        builder.transform(CenterCrop(), RoundedCorners(16))
    } else {
        builder.transform(CenterCrop())
    }
    builder.sizeMultiplier(0.6f).into(this)
}