package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

interface Repository {
    fun fillEmpty(status:Boolean):MutableList<Cell>
    fun pressCell(list: MutableList<Cell>, id: Int): MutableList<Cell>
    fun addMyShip(list: MutableList<Cell>, id:Int): MutableList<Cell>

    fun installEnemyShip(list: MutableList<Cell>, idShip: Int): MutableList<Cell>
    fun installEnvironment(enemyShipsForInstall: MutableList<Int>, idShip: Int): MutableList<Int>
    fun hitEnemy(list: MutableList<Cell>, id: Int, flag:Boolean): MutableList<Cell>
    fun attackOfEnemy(list: MutableList<Cell>, idShip: Int, flag: Boolean): MutableList<Cell>
    fun deleteEnvironment(enemyStep: MutableList<Int>, idShip: Int): MutableList<Int>
}