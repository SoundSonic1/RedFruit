package com.example.redfruit.ui.shared

import com.example.redfruit.data.model.Post

interface OnPostClickHandler {

    fun onTitleClick(post: Post)

    fun onPreviewClick(post: Post)
}
