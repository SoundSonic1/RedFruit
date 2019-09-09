package com.example.redfruit.data.api

/**
 * We want our repos to fetch data
 */
interface IRepository<T> {
    fun getData(sub: String, sortBy: String, limit: Int): T
}