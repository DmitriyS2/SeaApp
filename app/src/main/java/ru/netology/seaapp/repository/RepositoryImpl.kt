package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

class RepositoryImpl : Repository {

    private var count = 1
    private val maxField: Int = 100
    private val listEnvironment: List<Int> = listOf(-11, -10, -9, -1, 1, 9, 10, 11)

    override fun fillEmpty(status: Boolean): MutableList<Cell> {
        val list: MutableList<Cell> = mutableListOf()
        for (i in 1..maxField) {
            list.add(Cell(id = count, itIsMe = status))
            count++
        }

        if (count == 200) count = 1

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

    override fun installEnemyShip(list: MutableList<Cell>, idShip: Int): MutableList<Cell> {
        val newList: MutableList<Cell> = list.map {
            it.copy(
                isLiveShip = if (it.id == idShip) true else it.isLiveShip,
                isEmpty = if (it.id == idShip) false else it.isEmpty,
            )
        }.toMutableList()
        return newList
    }

    override fun installEnvironment(
        enemyShipsForInstall: MutableList<Int>,
        idShip: Int
    ): MutableList<Int> {
        val y = idShip % 10
        for (i in listEnvironment) {
            val number = idShip + i
            if ((number > 200) || (number < 100) || ((y == 0) && (i == -9 || i == 1 || i == 11)) || ((y == 1) && (i == -11 || i == -1 || i == 9))) {
                continue
            } else {
                enemyShipsForInstall.remove(number)
            }
        }
        return enemyShipsForInstall
    }

    override fun hitEnemy(list: MutableList<Cell>, id: Int): MutableList<Cell> {
        val flag = list.find {
            it.id == id
        }?.isLiveShip ?: false

//        if (list.find {
//                it.id == id
//            }?.isLiveShip == false) {
        if(!flag) {
            val newList: MutableList<Cell> = list.map {
                it.copy(
                    isMissed = if (it.id == id) true else it.isMissed,
                    isEmpty = if (it.id == id) false else it.isEmpty,
                    isChecked = if (it.id == id) false else it.isChecked
                )
            }.toMutableList()
            return newList
        } else {
            var newList: MutableList<Cell> = list.map {
                it.copy(
                    isEmpty = if (it.id == id) false else it.isEmpty,
                    isChecked = if (it.id == id) false else it.isChecked,
                    isLiveShip = if (it.id == id) false else it.isLiveShip,
                    isDeadShip = if (it.id == id) true else it.isDeadShip
                )
            }.toMutableList()

            val y = id % 10
            for (i in listEnvironment) {
                val number = id + i
                if ((number > 200) || (number < 100) || ((y == 0) && (i == -9 || i == 1 || i == 11)) || ((y == 1) && (i == -11 || i == -1 || i == 9))) {
                    continue
                } else {
                    newList = newList.map {
                        it.copy(
                      //      isDoNotAdd = if (it.id == number) flag else it.isDoNotAdd,
                            isEmpty = if(it.id==number) false else it.isEmpty,
                            isMissed =  if(it.id==number) true else it.isMissed
//                            isChecked = if(it.id==id) !it.isChecked else it.isChecked,
//                            isLiveShip = if(it.id==id) !it.isLiveShip else it.isLiveShip
                        )
                    }.toMutableList()
                }
            }
            return newList
        }
    }
}