package com.example.redfruit.repositories

import com.example.redfruit.data.repositories.CommentsRepository
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CommentsRepositoryTest {

    private val redditApi = provideRedditApi()

    @Test
    fun `archived post comments`() {

        runBlocking {
            val repo = CommentsRepository(
                redditApi,
                "redditdev",
                "9ncg2r"
            )
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
        var newRepo =
            CommentsRepository(redditApi, "redditdev", "")
        runBlocking {
            assertTrue(newRepo.getComments(50).isEmpty())
        }

        newRepo = CommentsRepository(redditApi, "", "123456")
        runBlocking {
            assertTrue(newRepo.getComments(50).isEmpty())
        }
    }

    @Test
    fun `large comment count`() {
        // contains kind: more
        val newRepo = CommentsRepository(
            redditApi,
            "grandorder",
            "d2n1a8"
        )
        runBlocking {
            assertTrue(newRepo.getComments(100).isNotEmpty())
        }
    }
}
