package com.example.redfruit.ui.home.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import com.example.redfruit.R
import com.example.redfruit.util.Constants

/**
 * Adapter to display subreddit suggestions in the SearchView
 */
class SuggestionsAdapter(
    context: Context?,
    cursor: Cursor?,
    flags: Int
) : CursorAdapter(context, cursor, flags) {

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val suggestionTextView = view?.findViewById<TextView>(R.id.suggestionsTextView)

        cursor?.getString(cursor.getColumnIndex(Constants.SUGGESTIONS))?.let {
            suggestionTextView?.text = it
        }

    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.suggestions_item, parent, false)
    }
}