package ru.netology.seaapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
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

class GameFragment : Fragment() {

    val viewmodel: MainViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameBinding.inflate(inflater, container, false)

        requireActivity().window.statusBarColor = requireContext().getColor(R.color.grey)

        val adapterMy = CellAdapter(object : Listener {
            override fun clickCell(cell: Cell) {
                //    TODO("Not yet implemented")
            }
        })
        val adapterEnemy = CellAdapter(object : Listener {
            override fun clickCell(cell: Cell) {
                //    TODO("Not yet implemented")
            }
        })

        binding.apply {

            rcMyView.layoutManager = GridLayoutManager(activity, 10)
            rcMyView.adapter = adapterMy
            rcCompView.layoutManager = GridLayoutManager(activity, 10)
            rcCompView.adapter = adapterEnemy
            //      rcCompView.visibility = View.GONE

            viewmodel.myShipsCount.observe(viewLifecycleOwner) {
                textMyField.text = "Мое корабли: $it"
            }

            viewmodel.enemyShipsCount.observe(viewLifecycleOwner) {
                textCompField.text = "Корабли противника: $it"
            }

            viewmodel.myShips.observe(viewLifecycleOwner) {
                adapterMy.submitList(it.listCell)
            }
            viewmodel.enemyShips.observe(viewLifecycleOwner) {
                adapterEnemy.submitList(it.listCell)
            }
        }
//            buttonPlus.setOnClickListener {
//                if (countMyShip < maxShip) {
//                    if (isCoordinatTrue()) {
//                        val x = edA.text.toString().toInt()
//                        val y = edB.text.toString().toInt()
//                        val pos = ((x + 1) * 11 + (y + 1))
//
//                        adapterMy.changeCell("#0d18a8", position = pos)
//                        edA.setText("")
//                        edB.setText("")
//                        countMyShip++
//                        textCountShip.text = "${countMyShip}й корабль"
//                        if (countMyShip == maxShip) {
//                            textInputLayoutA.visibility = View.GONE
//                            textInputLayoutB.visibility = View.GONE
//                            textCountShip.text = "ГОТОВО!"
//                            buttonPlus.text = "OK"
//                        }
//                    }
//                }
//
//            }


        return binding.root
    }
//    private fun isCoordinatTrue(): Boolean {
//        var flag = true
//        binding.apply {
//            if (edA.text.isNullOrEmpty()) {
//                edA.error = "Поле должно быть заполнено"
//                flag = false
//            } else if (edA.text.toString().toInt() < 0 || edA.text.toString().toInt() > 9) {
//                edA.error = "Значения от 0 до 9"
//                flag = false
//            }
//            if (edB.text.isNullOrEmpty()) {
//                edB.error = "Поле должно быть заполнено"
//                flag = false
//            } else if (edB.text.toString().toInt() < 0 || edB.text.toString().toInt() > 9) {
//                edB.error = "Значения от 0 до 9"
//                flag = false
//            }
//            return flag
//        }
//    }
//    private fun fillField(adapter: CellAdapter, colorBack: String, colorBackBorder: String) {
//        for (index1 in 0..120) {
//            val cell = Cell(" ", "#FF000000", colorBack)
//            when {
//                index1 in 1..10 -> {
//                    cell.text = (index1 - 1).toString()
//                    cell.backColor = colorBackBorder
//                }
//
//                index1 % 11 == 0 && index1 != 0 -> {
//                    cell.text = (index1 / 11 - 1).toString()
//                    cell.backColor = colorBackBorder
//                }
//
//                index1 == 0 -> {
//                    cell.backColor = colorBackBorder
//                }
//            }
//            adapter.addCell(cell)
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}