package com.example.redfruit.repositories

import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.repositories.CommentsRepository
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CommentsRepositoryTest {

    private val redditApi = provideRedditApi()
    private val preview = Preview(false, listOf())

    @Test
    fun `archived post comments`() {

        val post = Post(
            subreddit = "redditdev",
            id = "9ncg2r",
            preview = preview,
            gildings = Gildings(),
            url = ""
        )

        runBlocking {
            val repo = CommentsRepository(redditApi, post)
            val comments = repo.getComments(50)

            assertEquals(3, comments.size)

            val firstComment = comments[0]

            assertEquals("Watchful1", firstComment.author)

            assertEquals(
                "It looks like it's encoding.\n\n`https://preview.redd.it/b561b7thjlr11.jpg?auto=webp&amp;s=41918f5d043abced1aa923ee57eef49566c0c2fc`\n\n`https://preview.redd.it/b561b7thjlr11.jpg?auto=webp&s=41918f5d043abced1aa923ee57eef49566c0c2fc`\n\nThe `&` symbol means \"start an encoded entity\", so it can't be used by itself. To escape it, you need to put `&amp;`. It looks like chrome, at least for me, isn't properly parsing that, and your code might not be either.",
                firstComment.body
            )

            assertEquals("e7l7wy2", firstComment.id)

            assertEquals(0, firstComment.gildings.goldCount)

            assertEquals(1539311220, firstComment.created)

            assertEquals(1539282420, firstComment.created_utc)

            assertEquals(0, firstComment.depth)

            assertEquals(2, firstComment.replies.size, "Comment has two replies.")

            val nestedComment = firstComment.replies[1]

            assertEquals(1, nestedComment.replies.size, "Nested comment has one reply")

            val secondComment = comments[1]

            assertEquals("egphilippov", secondComment.author)
            assertTrue(secondComment.replies.isEmpty(), "Comment has no replies.")
        }
    }

    @Test
    fun `wrong input`() {

        var post = Post(
            subreddit = "redditdev",
            id = "",
            preview = preview,
            gildings = Gildings(),
            url = ""
        )

        var newRepo =
            CommentsRepository(redditApi, post)
        runBlocking {
            assertTrue(newRepo.getComments(50).isEmpty())
        }

        post = Post(
            subreddit = "",
            id = "123456",
            preview = preview,
            gildings = Gildings(),
            url = ""
        )

        newRepo = CommentsRepository(redditApi, post)
        runBlocking {
            assertTrue(newRepo.getComments(50).isEmpty())
        }
    }

    @Test
    fun `large comment count`() {

        val post = Post(
            subreddit = "grandorder",
            id = "d2n1a8",
            preview = preview,
            gildings = Gildings(),
            url = ""
        )
        // contains kind: more
        val newRepo = CommentsRepository(
            redditApi,
            post
        )
        runBlocking {
            assertTrue(newRepo.getComments(100).isNotEmpty())
        }
    }
}
