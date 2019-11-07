package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Repository which manages subreddit posts
 */
class SubredditPostsRepository(
    private val redditApi: RedditApi,
    private val subredditMap: MutableMap<Pair<String, SortBy>, SubredditListing> = mutableMapOf()
) : IPostsRepository {

    /**
     * Main function that returns a list of posts from a given subreddit
     * for ViewModels
     *
     * @param sub name of the subreddit
     * @param sortBy how the posts should be sorted
     * @param limit the amount of the posts that are to be fetched
     * @return a list of reddit posts
     */
    override suspend fun getPosts(sub: String, sortBy: SortBy, limit: Int): List<Post> {

        val subreddit = subredditMap.getOrPut(Pair(sub, sortBy)) {
            SubredditListing(sub)
        }

        val responseListing = try {
            redditApi.getSubredditListing(sub, sortBy.name, subreddit.after, limit.toString()) ?: return listOf()
        } catch (e: Throwable) {
            return listOf()
        }

        // we are at the bottom of the sub and already fetched the posts
        if (responseListing.after.isBlank() && subreddit.children.isNotEmpty()) {
            return subreddit.children.toList()
        }

        subreddit.apply {
            children.addAll(responseListing.children)
            after = responseListing.after
        }

        return subreddit.children.toList()
    }

    /**
     * Clear all data to make a clean refresh
     */
    override fun clearPosts() = subredditMap.clear()

}