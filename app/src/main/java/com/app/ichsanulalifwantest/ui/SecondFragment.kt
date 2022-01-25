package com.app.ichsanulalifwantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.ichsanulalifwantest.R
import com.app.ichsanulalifwantest.data.model.UserEntity
import com.app.ichsanulalifwantest.databinding.FragmentSecondBinding
import com.app.ichsanulalifwantest.viewmodel.ViewModelFactory

class SecondFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        // Init ViewModel
        val factory = ViewModelFactory.getInstance(requireActivity())
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getUserInput(1).observe(viewLifecycleOwner, {
            populateData(it)
        })

        // Back button action
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    /**
     * Set initial value from user input
     */
    private fun populateData(data: UserEntity) {
        with(binding) {
            tvName.text = data.name
            tvAge.text = data.age
            tvCity.text = data.city
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}