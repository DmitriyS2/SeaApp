package ru.netology.seaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import ru.netology.seaapp.dto.Cell
import ru.netology.seaapp.model.ShipModel
import ru.netology.seaapp.repository.Repository
import ru.netology.seaapp.repository.RepositoryImpl

class MainViewModel : ViewModel() {
    private val repository:Repository = RepositoryImpl()

    val myShips: MutableLiveData<ShipModel> = MutableLiveData()
    val enemyShips: MutableLiveData<ShipModel> = MutableLiveData()

    val status:MutableLiveData<Int> = MutableLiveData(0)

    val myShipsCount:MutableLiveData<Int> = MutableLiveData(myShips.value?.listCell?.count {
        it.itIsMe && it.isLiveShip
    } ?: 0)
    val enemyShipsCount:MutableLiveData<Int> = MutableLiveData(enemyShips.value?.listCell?.count {
        !it.itIsMe && it.isLiveShip
    } ?: 0)


    init {
        myShips.value = ShipModel(listCell = repository.fillEmpty(true), itIsMe = true)
        enemyShips.value = ShipModel(listCell = repository.fillEmpty(false), itIsMe = false)

//        val d = myShips.switchMap {
//            myShips
//            }


//        myShipsCount.value = myShips.value?.listCell?.count {
//            it.itIsMe && it.isLiveShip
//        } ?: 0
    }

}