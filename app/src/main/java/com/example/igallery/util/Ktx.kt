package com.example.igallery.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


fun ImageView.loadImage(path: String) {
    Glide.with(this.context).load(path).into(this)
}


fun ImageView.loadThumbnail(uri: Uri) {
    Glide.with(this.context).load(uri).into(this)
}


fun ImageView.loadThumbnail(path: String) {
    val file = File(path)
    Glide.with(this.context).load(file).into(this)
}