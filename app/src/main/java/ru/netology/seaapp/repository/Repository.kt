package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

interface Repository {
    fun fillEmpty(status:Boolean):MutableList<Cell>
    fun pressCell(list: MutableList<Cell>, id: Int): MutableList<Cell>
    fun addMyShip(list: MutableList<Cell>, id:Int): MutableList<Cell>
}