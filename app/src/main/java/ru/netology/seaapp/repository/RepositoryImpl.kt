package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

class RepositoryImpl : Repository {

    private var count = 1
    private val listEnvironment: List<Int> = listOf(-11, -10, -9, -1, 1, 9, 10, 11)

    override fun fillEmpty(status: Boolean): MutableList<Cell> {
        val list: MutableList<Cell> = mutableListOf()
        for (i in 1..100) {
            list.add(Cell(id = count, itIsMe = status))
            count++
        }
        return list
    }

    override fun pressCell(list: MutableList<Cell>, id: Int): MutableList<Cell> {
        val newList: MutableList<Cell> = list.map {
            it.copy(
                isChecked = if (it.id == id) !it.isChecked else it.isChecked
            )
        }.toMutableList()
        return newList
    }

    override fun addMyShip(list: MutableList<Cell>, id: Int): MutableList<Cell> {

        if (list[id - 1].isDoNotAdd) {
            return pressCell(list, id)
        }
        var flag = false

        if (list[id - 1].isEmpty) {
            flag = true
        }
        if (list[id - 1].isLiveShip) {
            flag = false
        }
        var newList: MutableList<Cell> = list.map {
            it.copy(
                isEmpty = if (it.id == id) !it.isEmpty else it.isEmpty,
                isChecked = if (it.id == id) !it.isChecked else it.isChecked,
                isLiveShip = if (it.id == id) !it.isLiveShip else it.isLiveShip
            )
        }.toMutableList()

        val y = id % 10
        for (i in listEnvironment) {
            val number = id + i
            if ((number > 100) || (number < 0) || ((y == 0) && (i == -9 || i == 1 || i == 11)) || ((y == 1) && (i == -11 || i == -1 || i == 9))) {
                continue
            } else {
                newList = newList.map {
                    it.copy(
                        isDoNotAdd = if (it.id == number) flag else it.isDoNotAdd,
//                            isEmpty = if(it.id==id) !it.isEmpty else it.isEmpty,
//                            isChecked = if(it.id==id) !it.isChecked else it.isChecked,
//                            isLiveShip = if(it.id==id) !it.isLiveShip else it.isLiveShip
                    )
                }.toMutableList()
            }
        }
        return newList
    }
}