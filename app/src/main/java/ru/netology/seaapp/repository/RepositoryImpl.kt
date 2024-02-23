package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

class RepositoryImpl:Repository {

    override fun fillEmpty(status:Boolean): List<Cell> {
        val list:MutableList<Cell> = mutableListOf()
        for(i in 1..100) {
            list.add(Cell(id=i, itIsMe = status))
        }
        return list
    }
}