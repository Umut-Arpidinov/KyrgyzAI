package edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.saveWords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.alatoo.kyrgyzlearning.databinding.ItemWordToSaveBinding

class SaveWordsAdapter : ListAdapter<String, SaveWordsAdapter.BranchViewHolder>(DiffUtils) {

    private val selectedWords = mutableSetOf<String>() // Stores selected words


    inner class BranchViewHolder(val binding: ItemWordToSaveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: String) = with(binding) {
            cbWord.text = word
            cbWord.isChecked = selectedWords.contains(word) // Restore checked state

            cbWord.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedWords.add(word) // Add selected word
                } else {
                    selectedWords.remove(word) // Remove if unchecked
                }
            }
        }
    }

    // Function to get selected words
    fun getSelectedWords(): List<String> {
        return selectedWords.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding =
            ItemWordToSaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchViewHolder(binding)

    }


    override fun onBindViewHolder(holder: SaveWordsAdapter.BranchViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {

        object DiffUtils : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }


}