package com.dicoding.jetlaptop.di

import com.dicoding.jetlaptop.data.LaptopRepository

object Injection {

    fun provideRepository(): LaptopRepository {
        return LaptopRepository.getInstance()
    }
}