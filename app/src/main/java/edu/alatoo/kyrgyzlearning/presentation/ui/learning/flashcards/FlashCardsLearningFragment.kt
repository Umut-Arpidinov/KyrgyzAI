package edu.alatoo.kyrgyzlearning.presentation.ui.learning.flashcards

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.data.local.db.entities.FlashCard
import edu.alatoo.kyrgyzlearning.databinding.FragmentFlashCardsLearningBinding
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.LearningViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.adapter.FlashCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlashCardsLearningFragment :
    BaseFragment<LearningViewModel, FragmentFlashCardsLearningBinding>(
        FragmentFlashCardsLearningBinding::inflate
    ) {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FlashCardAdapter
    override val viewModel: LearningViewModel by viewModel()


    override fun initClicks() = with(binding) {
        super.initClicks()
        ivNext.setOnClickListener {
            val current = viewPager.currentItem
            if (current < adapter.itemCount - 1) {
                viewPager.currentItem = current + 1
            }
        }

        ivPrevious.setOnClickListener {
            val current = viewPager.currentItem
            if (current > 0) {
                viewPager.currentItem = current - 1
            }
        }
    }

    override fun initialize() = with(binding) {
        super.initialize()

    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.wordWithDetails.observe(viewLifecycleOwner) {
            initViewPager(it)
        }
    }

    private fun initViewPager(
        list: List<FlashCard>
    ) = with(binding) {
        adapter = FlashCardAdapter()
        flashcardViewPager.isUserInputEnabled = false
        viewPager = binding.flashcardViewPager
        adapter.submitList(list)
        viewPager.adapter = adapter
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        val margin = resources.getDimensionPixelSize(R.dimen._16)
        viewPager.addItemDecoration(HorizontalMarginItemDecoration(margin))
    }

}

class HorizontalMarginItemDecoration(
    private val horizontalMarginInPx: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Отступы слева и справа
        outRect.left = horizontalMarginInPx
        outRect.right = horizontalMarginInPx
    }
}