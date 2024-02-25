package ru.netology.seaapp.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ru.netology.seaapp.R
import ru.netology.seaapp.adapter.CellAdapter
import ru.netology.seaapp.adapter.Listener
import ru.netology.seaapp.databinding.FragmentGameBinding
import ru.netology.seaapp.dto.Cell
import ru.netology.seaapp.viewmodel.MainViewModel

const val maxShips: Int = 10

class GameFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    val listStatus: List<String> = listOf(
        "Постановка своих кораблей",
        "Постановка кораблей противником",
        "Мой ход",
        "Ход противника",
        "Победил!!!",
        "Проиграл"
    )

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameBinding.inflate(inflater, container, false)

        requireActivity().window.statusBarColor = requireContext().getColor(R.color.grey)

        val adapterMy = CellAdapter(object : Listener {
            override fun clickCell(cell: Cell) {
                if (viewModel.status.value == 0) {
                    viewModel.pressCell(cell)
                }

            }
        })
        val adapterEnemy = CellAdapter(object : Listener {
            override fun clickCell(cell: Cell) {
                if (viewModel.status.value == 2) {
                    viewModel.pressCell(cell)
                }

            }
        })

        binding.apply {

            rcMyView.layoutManager = GridLayoutManager(activity, 10)
            rcMyView.adapter = adapterMy
            rcCompView.layoutManager = GridLayoutManager(activity, 10)
            rcCompView.adapter = adapterEnemy

            viewModel.myShipsCount.observe(viewLifecycleOwner) {
                textMyField.text = "Мои корабли: $it"
                if (viewModel.status.value == 0 && it == maxShips) {
                    viewModel.status.value = 1 //установка комп кораблей
                    viewModel.installEnemyShips()
                }
                if (viewModel.status.value != 0 && it == 0) {
                    viewModel.status.value = 5 //проигрыш
                }
            }

            viewModel.enemyShipsCount.observe(viewLifecycleOwner) {
                textCompField.text = "Корабли противника: $it"
                if (viewModel.status.value == 1 && it == maxShips) {
                    viewModel.status.value = 2 //мой ход
                }
                if (viewModel.status.value != 0 && viewModel.status.value != 1 && it == 0) {
                    viewModel.status.value = 4 //выигрыш
                }
            }

            viewModel.myShips.observe(viewLifecycleOwner) {
                adapterMy.submitList(it.listCell)
            }
            viewModel.enemyShips.observe(viewLifecycleOwner) {
                adapterEnemy.submitList(it.listCell)
            }

            viewModel.idPressedCheck.observe(viewLifecycleOwner) {
                buttonOk.isEnabled = it != 0
                Log.d("MyLog", "idPressedCheck=$it enabled=${buttonOk.isEnabled}")
            }

            buttonOk.setOnClickListener {
                ObjectAnimator.ofPropertyValuesHolder(
                    buttonOk,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0F, 1.3F, 1.0F),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0F, 1.3F, 1.0F)
                ).start()
//                Log.d(
//                    "MyLog",
//                    "enemyStep=${viewmodel.enemyStep} size = ${viewmodel.enemyStep.size}"
//                )
                when (viewModel.status.value) {
                    0 -> {
                        viewModel.addMyShip()
                    }

                    2 -> {

                       val t = viewModel.hitEnemy()
                        Log.d("MyLog", "hitEnemy=$t")
//                        if(!viewModel.hitEnemy()) {
//                            viewModel.status.value=3
//                        }
                    }
                }
            }

            viewModel.status.observe(viewLifecycleOwner) {
                textStatus.text = listStatus[it]
                Log.d("MyLog", "status=$it")
                when (it) {
                    0 -> {

                    }

                    1 -> {
                        buttonOk.isEnabled = false
                    }

                    2 -> {
                        Log.d(
                            "MyLog",
                            "enemyShipsForInstall=${viewModel.enemyShipsForInstall}, size=${viewModel.enemyShipsForInstall.size}"
                        )
                    }

                    3 -> {
                        buttonOk.isEnabled = false
                    }

                    4 -> {

                    }

                    5 -> {

                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}