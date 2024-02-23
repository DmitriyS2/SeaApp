package ru.netology.seaapp.dto

data class Cell(
    val id:Int,
    val itIsMe:Boolean,
    val isLiveShip:Boolean = false,
    val isDeadShip:Boolean = false,
    val isEmpty:Boolean = true,
    val isMissed:Boolean = false,
//    var text:String,
//    var textColor:String,
//    var backColor:String
)

