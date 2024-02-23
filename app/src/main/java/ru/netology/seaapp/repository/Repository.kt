package ru.netology.seaapp.repository

import ru.netology.seaapp.dto.Cell

interface Repository {
    fun fillEmpty(status:Boolean):List<Cell>
}