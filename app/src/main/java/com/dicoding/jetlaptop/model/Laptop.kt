package com.dicoding.jetlaptop.model

data class Laptop(

    val id: Int,

    val image: Int,

    val price: Int,

    val name: String,

    val brand: String,

    val description: String,

    val rating: Double,

    var isFavorite: Boolean = false
)