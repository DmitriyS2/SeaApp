package ru.netology.seaapp.model

import ru.netology.seaapp.dto.Cell

data class ShipModel(
    val listCell:MutableList<Cell> = mutableListOf(),
    val itIsMe:Boolean
)
