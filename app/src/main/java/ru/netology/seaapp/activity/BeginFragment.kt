package ru.netology.seaapp.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.netology.seaapp.R
import ru.netology.seaapp.databinding.FragmentBeginBinding

class BeginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBeginBinding.inflate(inflater, container, false)

        binding.apply {
            textSea.alpha = 0f
            textBattle.alpha = 0f
            textSea.text = "МОРСКОЙ"
            textBattle.text = "БОЙ"
            textSea.animate().alpha(1f).translationYBy(-50f).setStartDelay(300).duration = 1500
            textBattle.animate().alpha(1f).translationYBy(-50f).setStartDelay(300).duration = 1500

            buttonStart.alpha = 0f
            buttonStart.animate().alpha(1f).translationYBy(-50f).setStartDelay(1500).duration = 1500

            buttonStart.setOnClickListener {
                findNavController().navigate(R.id.gameFragment)
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BeginFragment()
    }
}