package ru.netology.seaapp.model

import ru.netology.seaapp.dto.Cell

data class ShipModel(
    val listCell:List<Cell> = emptyList(),
    val itIsMe:Boolean
)
