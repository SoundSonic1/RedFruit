package com.example.redfruit

import com.example.redfruit.data.api.CommentsRepository
import org.junit.Assert
import org.junit.Test

class CommentsRepositoryTest {

    @Test
    fun testArchivedPostComments() {
        val repo = CommentsRepository("redditdev", "9ncg2r")
        val comments = repo.getComments(50)

        Assert.assertEquals(3, comments.size)

        val firstComment = comments[0]

        Assert.assertEquals("Watchful1", firstComment.author)

        Assert.assertEquals(
            "It looks like it's encoding.\n\n`https://preview.redd.it/b561b7thjlr11.jpg?auto=webp&amp;s=41918f5d043abced1aa923ee57eef49566c0c2fc`\n\n`https://preview.redd.it/b561b7thjlr11.jpg?auto=webp&s=41918f5d043abced1aa923ee57eef49566c0c2fc`\n\nThe `&` symbol means \"start an encoded entity\", so it can't be used by itself. To escape it, you need to put `&amp;`. It looks like chrome, at least for me, isn't properly parsing that, and your code might not be either.",
            firstComment.body
        )

        Assert.assertEquals("e7l7wy2", firstComment.id)

        Assert.assertEquals(0, firstComment.gildings.goldCount)

        Assert.assertEquals("Comment has two replies.",2, firstComment.replies.size)

        val nestedComment = firstComment.replies[1]

        Assert.assertEquals("Nested comment has one reply",1, nestedComment.replies.size)

        val secondComment = comments[1]

        Assert.assertEquals("egphilippov", secondComment.author)
        Assert.assertEquals("Comment has no replies.",0, secondComment.replies.size)
    }

    @Test
    fun testWrongInput() {
        var repo = CommentsRepository("redditdev", "")

        Assert.assertEquals(0, repo.getComments(50).size)

        repo = CommentsRepository("", "123456")
        Assert.assertEquals(0, repo.getComments(50).size)
    }

    @Test
    fun testLargeCommentCount() {
        // contains kind: more
        val repo = CommentsRepository("grandorder", "d2n1a8")
        val comments = repo.getComments(100)
        Assert.assertTrue(comments.isNotEmpty())
    }
}