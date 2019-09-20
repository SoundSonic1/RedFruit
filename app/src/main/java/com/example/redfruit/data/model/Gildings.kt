package com.example.redfruit.data.model

import com.google.gson.annotations.SerializedName

data class Gildings(
    @SerializedName("gid_1")
    val silverCount: Int = 0,
    @SerializedName("gid_2")
    val goldCount: Int = 0,
    @SerializedName("gid_3")
    val platinumCount: Int = 0
)
