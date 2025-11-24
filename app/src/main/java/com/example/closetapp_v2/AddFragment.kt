package com.example.closetapp_v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import database.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    //NAVIGATION????????????????????????
    private fun navigateToListFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.layoutFragment, ListFragment())
            .commit()
    }



    //
    // go back to list when these buttons are pressed
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[ClosetItemViewModel::class.java]
        val inputName = view.findViewById<EditText>(R.id.inputName)
        val inputStyle = view.findViewById<EditText>(R.id.inputStyle)
        val inputMaterial = view.findViewById<EditText>(R.id.inputMaterial)
        val inputColor = view.findViewById<EditText>(R.id.inputColor)
        val inputComfort = view.findViewById<EditText>(R.id.inputComfort)
        val inputOccasion = view.findViewById<EditText>(R.id.inputOccasion)

        val saveButton = view.findViewById<Button>(R.id.addSaveButton)
        val cancelButton = view.findViewById<Button>(R.id.addCancelButton)

        saveButton.setOnClickListener {
            //saving <3
            val item = Item(
                name = inputName.text.toString(),
                style = inputStyle.text.toString(),
                material = inputMaterial.text.toString(),
                color = inputColor.text.toString(),
                comfort = inputComfort.text.toString().toIntOrNull() ?: 5,
                occasion = inputOccasion.text.toString()
            )

            viewModel.addItem(item)
            Toast.makeText(requireContext(), "Added! <3", Toast.LENGTH_SHORT).show()
            navigateToListFragment()
        }

        cancelButton.setOnClickListener {
            navigateToListFragment()
        }
    }


    companion object {
    @JvmStatic
    fun newInstance() = AddFragment()
    }
}