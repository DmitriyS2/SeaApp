package ru.netology.seaapp.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.seaapp.R
import ru.netology.seaapp.databinding.FragmentEndBinding
import ru.netology.seaapp.viewmodel.MainViewModel

class EndFragment : Fragment() {

    private val viewModel:MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      val binding = FragmentEndBinding.inflate(inflater, container, false)

        requireActivity().window.statusBarColor = requireContext().getColor(R.color.white)

        viewModel.status.observe(viewLifecycleOwner) {
            when(it) {
                4 -> {
                    binding.text1.text = "ПОБЕДА!!!"
                    binding.image1.setImageResource(R.drawable.pobeda)
                }
                5 -> {
                    binding.text1.text = "Не повезло..."
                    binding.image1.setImageResource(R.drawable.smile)
                }
            }
        }

        binding.buttonNewGame.setOnClickListener {
            viewModel.newGame()
            findNavController().navigate(R.id.action_endFragment_to_beginFragment)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EndFragment()
    }
}