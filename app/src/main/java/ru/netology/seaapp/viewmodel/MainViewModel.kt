package ru.netology.seaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.netology.seaapp.activity.maxShips
import ru.netology.seaapp.dto.Cell
import ru.netology.seaapp.model.ShipModel
import ru.netology.seaapp.repository.Repository
import ru.netology.seaapp.repository.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val repository: Repository = RepositoryImpl()

    val myShips: MutableLiveData<ShipModel> = MutableLiveData()
    val enemyShips: MutableLiveData<ShipModel> = MutableLiveData()
    val idPressedCheck: MutableLiveData<Int> = MutableLiveData(0)

    val status: MutableLiveData<Int> = MutableLiveData(0)

    val myShipsCount: MutableLiveData<Int> = MutableLiveData(myShips.value?.listCell?.count {
        it.itIsMe && it.isLiveShip
    } ?: 0)
    val enemyShipsCount: MutableLiveData<Int> = MutableLiveData(enemyShips.value?.listCell?.count {
        !it.itIsMe && it.isLiveShip
    } ?: 0)

    val enemyStep: MutableList<Int>
    var enemyShipsForInstall: MutableList<Int>


    init {
        myShips.value = ShipModel(listCell = repository.fillEmpty(true), itIsMe = true)
        enemyShips.value = ShipModel(listCell = repository.fillEmpty(false), itIsMe = false)
        enemyStep = myShips.value!!.listCell.map {
            it.id
        }.toMutableList()
        enemyShipsForInstall = enemyShips.value!!.listCell.map {
            it.id
        }.toMutableList()
//        val d = myShips.value!!.listCell[5]
        //        enemyStep.remove(101)


    }

    fun pressCell(cell: Cell) {
        if (idPressedCheck.value == 0 || idPressedCheck.value == cell.id) {
            idPressedCheck.value = if (!cell.isChecked) cell.id else 0
            if (cell.itIsMe) {
                myShips.value = ShipModel(
                    listCell = repository.pressCell(
                        myShips.value?.listCell.orEmpty().toMutableList(), cell.id
                    ), itIsMe = true
                )
            } else {
                enemyShips.value = ShipModel(
                    listCell = repository.pressCell(
                        enemyShips.value?.listCell.orEmpty().toMutableList(), cell.id
                    ), itIsMe = false
                )
            }
        }
    }

    fun addMyShip() {
        myShips.value = ShipModel(
            listCell = repository.addMyShip(
                myShips.value?.listCell.orEmpty().toMutableList(), idPressedCheck.value ?: 0
            ), itIsMe = true
        )
        idPressedCheck.value = 0

        myShipsCount.value = myShips.value?.listCell?.count {
            it.itIsMe && it.isLiveShip
        } ?: 0
    }

    fun installEnemyShips() {
        viewModelScope.launch {
            for (i in 1..maxShips) {
                delay(200)
                val idShip: Int = enemyShipsForInstall.random()
                enemyShips.value = ShipModel(
                    listCell = repository.installEnemyShip(
                        enemyShips.value?.listCell.orEmpty().toMutableList(), idShip
                    ), itIsMe = false
                )
                enemyShipsForInstall.remove(idShip)
                enemyShipsForInstall = repository.installEnvironment(enemyShipsForInstall, idShip)
                enemyShipsCount.value = enemyShips.value?.listCell?.count {
                    !it.itIsMe && it.isLiveShip
                } ?: 0
            }
        }
    }

    fun hitEnemy(): Boolean {

        val flag = enemyShips.value?.listCell?.find {
            it.id == idPressedCheck.value
        }?.isLiveShip ?: false

        enemyShips.value = ShipModel(
            listCell = repository.hitEnemy(
                enemyShips.value?.listCell.orEmpty().toMutableList(), idPressedCheck.value ?: 0
            ), itIsMe = false
        )
        idPressedCheck.value = 0

        enemyShipsCount.value = enemyShips.value?.listCell?.count {
            !it.itIsMe && it.isLiveShip
        } ?: 0

        return flag
    }

}