package com.example.redfruit.ui.shared

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.noties.markwon.Markwon

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("markDownText")
    fun loadMarkDownText(textView: TextView, text: String) =
        Markwon.create(textView.context).apply {
            setMarkdown(textView, text)
        }

    @JvmStatic
    @BindingAdapter("timecreated")
    fun setTimeCreated(textView: TextView, created: Long) {
        textView.text = DateUtils.getRelativeTimeSpanString(
            created * 1000,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        )
    }
}
