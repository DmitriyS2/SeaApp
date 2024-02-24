package ru.netology.seaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import ru.netology.seaapp.dto.Cell
import ru.netology.seaapp.model.ShipModel
import ru.netology.seaapp.repository.Repository
import ru.netology.seaapp.repository.RepositoryImpl

class MainViewModel : ViewModel() {
    private val repository:Repository = RepositoryImpl()

    val myShips: MutableLiveData<ShipModel> = MutableLiveData()
    val enemyShips: MutableLiveData<ShipModel> = MutableLiveData()
    val idPressedCheck:MutableLiveData<Int> = MutableLiveData(0)

    val status:MutableLiveData<Int> = MutableLiveData(0)

    val myShipsCount:MutableLiveData<Int> = MutableLiveData(myShips.value?.listCell?.count {
        it.itIsMe && it.isLiveShip
    } ?: 0)
    val enemyShipsCount:MutableLiveData<Int> = MutableLiveData(enemyShips.value?.listCell?.count {
        !it.itIsMe && it.isLiveShip
    } ?: 0)

    var enemyStep: MutableList<Int>


    init {
        myShips.value = ShipModel(listCell = repository.fillEmpty(true), itIsMe = true)
        enemyShips.value = ShipModel(listCell = repository.fillEmpty(false), itIsMe = false)
        enemyStep = myShips.value!!.listCell.map {
            it.id
        }.toMutableList()
     //   enemyStep.remove(myShips.value!!.listCell[2].id)


    }

    fun pressCell(cell: Cell) {
        if(idPressedCheck.value==0 || idPressedCheck.value==cell.id) {
            idPressedCheck.value = if(!cell.isChecked) cell.id else 0
            if (cell.itIsMe) {
                myShips.value = ShipModel(listCell = repository.pressCell(myShips.value?.listCell.orEmpty().toMutableList(), cell.id), itIsMe = true)
            } else {
                enemyShips.value = ShipModel(listCell = repository.pressCell(enemyShips.value?.listCell.orEmpty().toMutableList(), cell.id), itIsMe = false)
            }
        }
    }

    fun addMyShip() {
        myShips.value = ShipModel(listCell = repository.addMyShip( myShips.value?.listCell.orEmpty().toMutableList(),idPressedCheck.value ?: 0), itIsMe = true)
        idPressedCheck.value=0

        myShipsCount.value = myShips.value?.listCell?.count {
            it.itIsMe && it.isLiveShip
        } ?: 0
    }
}