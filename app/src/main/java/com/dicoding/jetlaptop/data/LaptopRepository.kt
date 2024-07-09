package com.dicoding.jetlaptop.data

import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.model.LaptopData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class LaptopRepository {

    private val dummyLaptop = mutableListOf<Laptop>()

    init {
        if (dummyLaptop.isEmpty()) {
            LaptopData.dummyLaptop.forEach {
                dummyLaptop.add(it)
            }
        }
    }

    fun getLaptopById(laptopId: Int): Laptop {
        return dummyLaptop.first {
            it.id == laptopId
        }
    }

    fun getFavoriteLaptop(): Flow<List<Laptop>> {
        return flowOf(dummyLaptop.filter { it.isFavorite })
    }

    fun searchLaptop(query: String) = flow {
        val data = dummyLaptop.filter {
            it.name.contains(query, ignoreCase = true)
        }

        emit(data)
    }

    fun updateLaptop(laptopId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyLaptop.indexOfFirst { it.id == laptopId }
        val result = if (index >= 0) {
            val laptop = dummyLaptop[index]
            dummyLaptop[index] = laptop.copy(isFavorite = newState)

            true
        } else {
            false
        }

        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: LaptopRepository? = null

        fun getInstance(): LaptopRepository =
            instance ?: synchronized(this) {
                LaptopRepository().apply {
                    instance = this
                }
            }
    }
}