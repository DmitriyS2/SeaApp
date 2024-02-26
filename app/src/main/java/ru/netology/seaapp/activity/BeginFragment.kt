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
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.blue)

        binding.apply {
            textSea.alpha = 0f
            textBattle.alpha = 0f
            textSea.text = "МОРСКОЙ"
            textBattle.text = "БОЙ"
            textSea.animate().alpha(1f).translationYBy(-50f).setStartDelay(300).duration = 1000
            textBattle.animate().alpha(1f).translationYBy(-50f).setStartDelay(300).duration = 1000

            buttonStart.alpha = 0f
            buttonStart.animate().alpha(1f).translationYBy(-50f).setStartDelay(1000).duration = 1000

            buttonStart.setOnClickListener {
                findNavController().navigate(R.id.action_beginFragment_to_gameFragment)
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BeginFragment()
    }
}