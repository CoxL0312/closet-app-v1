
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.closetapp_v2.ClosetItemViewModel
import com.example.closetapp_v2.ListFragment
import com.example.closetapp_v2.R
import com.example.closetapp_v2.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import database.Item


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: ClosetItemViewModel
    private var itemIndex: Int = -1
    private var isEditMode = false
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemIndex = arguments?.getInt("ITEM_INDEX") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ClosetItemViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setCurrentItem(itemIndex)

        viewModel.currentItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                binding.textName.text = "Name: ${it.name}"
                binding.inputName.setText(it.name)

                binding.textStyle.text = "Style: ${it.style}"
                binding.inputStyle.setText(it.style)

                binding.textMaterial.text = "Material: ${it.material}"
                binding.inputMaterial.setText(it.material)

                binding.textColor.text = "Color: ${it.color}"
                binding.inputColor.setText(it.color)

                binding.textComfort.text = "Comfort: ${it.comfort}"
                binding.inputComfort.setText(it.comfort.toString())

                binding.textOccasion.text = "Occasion: ${it.occasion}"
                binding.inputOccasion.setText(it.occasion)
            }
        }

        //never 4get 2 save
        binding.btnSave.setOnClickListener { view ->
            Snackbar.make(view, "Want to save these changes?", Snackbar.LENGTH_LONG)
                .setAction("Save") {
                    val currentId = viewModel.currentItem.value?.id ?: 0

                    val updatedItem = Item(
                        id = currentId,
                        name = binding.inputName.text.toString(),
                        style = binding.inputStyle.text.toString(),
                        material = binding.inputMaterial.text.toString(),
                        color = binding.inputColor.text.toString(),
                        comfort = binding.inputComfort.text.toString().toIntOrNull() ?: 5,
                        occasion = binding.inputOccasion.text.toString()
                    )
                    viewModel.updateItem(updatedItem)
                    Toast.makeText(requireContext(), "Saved!!!", Toast.LENGTH_SHORT).show()
                    toggleEditMode(false)
                }
                .show()
        }

        //are we editing or not
        binding.btnEdit.setOnClickListener {
            toggleEditMode(true)
        }

        //cancel all my plans joan
        binding.btnCancel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layoutFragment, ListFragment())
                .commit()
        }

        //delete his number rn girl
        binding.btnDelete.setOnClickListener {
            val currentItem = viewModel.currentItem.value
            if (currentItem != null) {
                viewModel.deleteItem(currentItem)
                Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()

                //navigating back to list
                parentFragmentManager.beginTransaction()
                    .replace(R.id.layoutFragment, ListFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Nothing to delete", Toast.LENGTH_SHORT).show()
            }
        }


        //view only
        toggleEditMode(false)
    }

    private fun toggleEditMode(editing: Boolean) {
    isEditMode = editing
        binding.editLayout.visibility = if (editing) View.VISIBLE else View.GONE
        binding.detailLayout.visibility = if (editing) View.GONE else View.VISIBLE
    }
    //a friend
    companion object {
        fun newInstance(itemId: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putInt("ITEM_INDEX", itemId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}







