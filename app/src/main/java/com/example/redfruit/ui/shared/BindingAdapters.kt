package com.example.redfruit.ui.shared

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.noties.markwon.Markwon

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("markDownText")
    fun loadMarkDownText(textView: TextView, text: String) {
        Markwon.create(textView.context).apply {
            setMarkdown(textView, text)
        }
    }
}
