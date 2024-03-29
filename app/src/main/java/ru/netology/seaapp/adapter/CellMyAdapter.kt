package ru.netology.seaapp.adapter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.seaapp.R
import ru.netology.seaapp.databinding.CellItemBinding
import ru.netology.seaapp.dto.Cell

interface Listener {
    fun clickCell(cell: Cell)
}

class CellAdapter(private val listener: Listener) :
    ListAdapter<Cell, CellAdapter.CellHolder>(CellDiffCallback()) {

    class CellHolder(item: View, private val listener: Listener) : RecyclerView.ViewHolder(item) {
        private val binding = CellItemBinding.bind(item)

        fun bind(cell: Cell) = with(binding) {

            imageCell.setImageResource(
                when {
                    cell.isEmpty -> R.drawable.empty_24
                    cell.isMissed -> {
                        ObjectAnimator.ofPropertyValuesHolder(
                            imageCell,
                            PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3F, 1.0F),
                            PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3F, 1.0F)
                        ).start()
                        (R.drawable.clear_24)
                    }
                    cell.isDeadShip -> {
                        ObjectAnimator.ofPropertyValuesHolder(
                            imageCell,
                            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0F, 1.9F, 1.0F),
                            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0F, 1.9F, 1.0F)
                        ).start()
                        (R.drawable.fire_24)
                    }
                    cell.itIsMe && cell.isLiveShip -> (R.drawable.ship_24)
                    !cell.itIsMe && cell.isLiveShip -> (R.drawable.empty_24)
               //      cell.isLiveShip -> (R.drawable.ship_24)
                 //   cell.isDoNotAdd -> R.drawable.clear_24
                    else -> 0
                }
            )
            imageCell.setOnClickListener {
                ObjectAnimator.ofPropertyValuesHolder(
                    imageCell,
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0F, 1.3F, 1.0F),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0F, 1.3F, 1.0F)
                ).start()
                listener.clickCell(cell)
            }

            imageCell.setBackgroundResource(if (cell.isChecked) R.color.blue else R.color.light_blue)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        return CellHolder(view, listener)
    }

    override fun onBindViewHolder(holder: CellHolder, position: Int) {
        val cell = getItem(position)
        holder.bind(cell)
    }
}

class CellDiffCallback : DiffUtil.ItemCallback<Cell>() {
    override fun areItemsTheSame(oldItem: Cell, newItem: Cell): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cell, newItem: Cell): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Cell, newItem: Cell): Any =
        Payload()
}

data class Payload(
    val id: Int? = null
)