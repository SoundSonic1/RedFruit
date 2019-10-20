package com.example.redfruit

import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.repositories.CommentsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*

class CommentsRepositoryTest {

    private val redditApi: IRedditApi =
        RedditApi(
            TokenAuthenticator(TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString())),
            Klaxon(),
            Parser.default()
        )

    @Test
    fun testArchivedPostComments() {
        runBlocking {
            val repo = CommentsRepository(
                redditApi,
                "redditdev",
                "9ncg2r"
            )
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
    }

    @Test
    fun testWrongInput() {
        var repo =
            CommentsRepository(redditApi, "redditdev", "")
        runBlocking {
            Assert.assertEquals(0, repo.getComments(50).size)
        }

        repo = CommentsRepository(redditApi, "", "123456")
        runBlocking {
            Assert.assertEquals(0, repo.getComments(50).size)
        }
    }

    @Test
    fun testLargeCommentCount() {
        // contains kind: more
        val repo = CommentsRepository(
            redditApi,
            "grandorder",
            "d2n1a8"
        )
        runBlocking {
            Assert.assertTrue(repo.getComments(100).isNotEmpty())
        }
    }
}