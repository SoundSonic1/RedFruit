package com.example.redfruit.data.api

interface IRepository<T> {
    fun getData(): Collection<T>
}