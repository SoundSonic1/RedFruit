package com.example.redfruit.data.api

/**
 * We want our repos to give us data in form of Collection<T>
 */
interface IRepository<T> {
    fun getData(url: String): T
}