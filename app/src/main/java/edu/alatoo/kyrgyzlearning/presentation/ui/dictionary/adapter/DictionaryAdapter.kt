package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.databinding.ItemWordBinding

class DictionaryAdapter : ListAdapter<Word, DictionaryAdapter.BranchViewHolder>(DiffUtils) {


    var onWordListener: ((Word) -> Unit)? = null

    inner class BranchViewHolder(val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) = with(binding) {
            tvWord.text = word.word
            root.setOnClickListener {
                onWordListener?.invoke(word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding =
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchViewHolder(binding)

    }


    override fun onBindViewHolder(holder: DictionaryAdapter.BranchViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {

        object DiffUtils : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }


}