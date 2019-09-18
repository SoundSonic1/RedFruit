package com.example.redfruit.data.api

import com.example.redfruit.data.model.enumeration.SortBy

/**
 * We want our repos to fetch data
 * @param T data type which is provided by the repository
 */
interface IPostsRepository<T> {
    fun getData(sub: String, sortBy: SortBy, limit: Int): T
    fun clearData()
}