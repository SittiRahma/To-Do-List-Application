package com.example.todolist.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.data.model.ToDoData

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel :SharedViewModel by viewModels()
    private val mToDoViewModel :ToDoViewModel by viewModels()

    private var binding : FragmentUpdateBinding? = null
    private val binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        setHasOptionsMenu(true)

        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_title_et.text.toString()
        val getPriority = current_priorities_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Update Sukses", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else {
            Toast.makeText(requireContext(), "Isi semua field", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setPositiveButton("Yes") {_, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Penghapusan sukses : ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_, _ ->}
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Apakah yakin ingin menghapus '${args.currentItem.title}'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}