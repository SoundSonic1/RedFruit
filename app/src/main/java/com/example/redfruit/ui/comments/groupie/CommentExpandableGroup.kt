package com.example.redfruit.ui.comments.groupie

import com.example.redfruit.data.model.Comment
import com.xwray.groupie.ExpandableGroup

/**
 * Use ExpandableGroupe to represent nested comments
 */
class CommentExpandableGroup(
    comment: Comment
) : ExpandableGroup(CommentItem(comment)) {

    init {
        comment.replies.forEach {
            add(CommentExpandableGroup(it))
        }
    }
}
