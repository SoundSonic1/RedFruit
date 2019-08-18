package com.example.redfruit.data.model.Interfaces

interface Listing<T> {
    var before: String
    var after: String
    var children: List<T>
}