package com.example.redfruit.util

interface IFactory<T> {
    fun build(): T
}