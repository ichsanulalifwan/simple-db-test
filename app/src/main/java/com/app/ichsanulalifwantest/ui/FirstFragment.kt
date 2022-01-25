package com.app.ichsanulalifwantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.ichsanulalifwantest.data.model.UserEntity
import com.app.ichsanulalifwantest.databinding.FragmentFirstBinding
import com.app.ichsanulalifwantest.viewmodel.ViewModelFactory

class FirstFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var userEntity: UserEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // Init ViewModel
        val factory = ViewModelFactory.getInstance(requireActivity())
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            // Submit button action
            buttonSubmit.setOnClickListener {

                progressBar.visibility = View.VISIBLE

                // Get Input value from editText
                val input = input.text.toString()

                // Validate Input
                if (validateInput(input)) {

                    val userInput = getInput(input.uppercase())
                    userViewModel.addInput(userInput)

                    // Check if input added
                    userViewModel.observableStatus.observe(viewLifecycleOwner, { data ->
                        check(data)
                    })
                }
            }
        }
    }

    /**
     * Get and extract user input to three data
     */
    private fun getInput(input: String): UserEntity {

        var startNum = 0
        var endNum = 0

        // access string index to check age type
        input.forEachIndexed { index, c ->
            val checkNumber = c.toString().matches(Regex("[0-9]")) && startNum == 0
            val checkAfterNumber = startNum != 0 && c == ' '
            when {
                checkNumber -> startNum = index
                checkAfterNumber -> {
                    endNum = index
                    return@forEachIndexed
                }
            }
        }

        val subName = input.subSequence(0, startNum).toString()
        val subAge = input.subSequence(startNum, endNum).toString()
        val subCIty = input.subSequence(endNum, input.length).toString()

        when {
            subCIty.contains("TAHUN") -> subAge.plus("TAHUN")
            subCIty.contains("THN") -> subAge.plus("THN")
            subCIty.contains("TH") -> subAge.plus("TH")
        }

        Toast.makeText(requireContext(), endNum.toString(), Toast.LENGTH_LONG).show()

        val id = if (userEntity != null) userEntity?.id else null

        return UserEntity(
            id = id,
            name = subName,
            age = subAge,
            city = subCIty
        )
    }

    /**
     * Validate User Name Input isEmpty or not
     */
    private fun validateInput(text: String): Boolean {
        with(binding) {
            if (text.isEmpty()) {

                inputUser.error = "Please add your input"
                inputUser.requestFocus()

                return false
            }

            return true
        }
    }

    /**
     * Check input added to database
     */
    private fun check(status: Boolean) {
        when (status) {
            true -> {
                binding.progressBar.visibility = View.GONE

                val actionNext = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
                findNavController().navigate(actionNext)

                Toast.makeText(requireContext(), "Input Added Successfully", Toast.LENGTH_LONG)
                    .show()
            }
            false -> Toast.makeText(requireContext(), "Input Add Failed", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}