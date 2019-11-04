package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Repository which manages subreddit posts
 */
class SubredditPostsRepository(
    private val redditApi: IRedditApi,
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

        val responsePair = redditApi.getSubredditPosts(sub, sortBy, subreddit.after, limit)

        // we are at the bottom of the sub and already fetched the posts
        if (responsePair.second.isBlank() && subreddit.children.isNotEmpty()) {
            return subreddit.children.toList()
        }

        subreddit.apply {
            children.addAll(responsePair.first)
            after = responsePair.second
        }

        return subreddit.children.toList()
    }

    /**
     * Clear all data to make a clean refresh
     */
    override fun clearPosts() = subredditMap.clear()

}