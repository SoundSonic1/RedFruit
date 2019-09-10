package com.example.redfruit.data.api

/**
 * We want our repos to fetch data
 * @param T data type which is provided by the repository
 */
interface IRepository<T> {
    fun getData(sub: String, sortBy: String, limit: Int): T
}