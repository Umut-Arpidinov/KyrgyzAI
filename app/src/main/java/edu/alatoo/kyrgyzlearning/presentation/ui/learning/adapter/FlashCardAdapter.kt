package edu.alatoo.kyrgyzlearning.presentation.ui.learning.adapter

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.data.local.db.entities.FlashCard
import edu.alatoo.kyrgyzlearning.databinding.ItemFlashCardBinding


class FlashCardAdapter : ListAdapter<FlashCard, FlashCardAdapter.FlashCardViewHolder>(DiffUtils) {

    inner class FlashCardViewHolder(val binding: ItemFlashCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(flashCard: FlashCard) = with(binding) {
            tvWord.text = flashCard.word
            tvTranslationPlaceholder.text = flashCard.translations
            binding.root.setOnClickListener {
                flipCard()
            }
        }

        private var isFront = true

        private fun flipCard() = with(binding) {
            val flipFront = AnimatorInflater.loadAnimator(
                itemView.context,
                R.animator.flip_front
            ) as AnimatorSet
            val flipBack =
                AnimatorInflater.loadAnimator(itemView.context, R.animator.flip_back) as AnimatorSet
            try {
                val scale = itemView.context.applicationContext.resources.displayMetrics.density
                frontCard.cameraDistance = 8000 * scale
                backCard.cameraDistance = 8000 * scale

                if (isFront) {
                    flipFront.setTarget(frontCard)
                    flipBack.setTarget(backCard)
                    flipFront.start()
                    flipBack.start()
                    isFront = false

                } else {
                    flipFront.setTarget(backCard)
                    flipBack.setTarget(frontCard)
                    flipBack.start()
                    flipFront.start()
                    isFront = true

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val binding =
            ItemFlashCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        object DiffUtils : DiffUtil.ItemCallback<FlashCard>() {
            override fun areItemsTheSame(oldItem: FlashCard, newItem: FlashCard): Boolean {
                return oldItem.word == newItem.word
            }

            override fun areContentsTheSame(oldItem: FlashCard, newItem: FlashCard): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }
}





