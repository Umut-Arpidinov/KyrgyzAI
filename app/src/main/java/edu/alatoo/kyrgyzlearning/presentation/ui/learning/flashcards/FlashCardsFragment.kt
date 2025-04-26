package edu.alatoo.kyrgyzlearning.presentation.ui.learning.flashcards

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.NavMainDirections
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentFlashcardsBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.navigateWithAnimation
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.LearningFragmentDirections
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.LearningViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlashCardsFragment : BaseFragment<LearningViewModel, FragmentFlashcardsBinding>(
    FragmentFlashcardsBinding::inflate
) {

    override val viewModel: LearningViewModel by viewModel()


    override fun initClicks() = with(binding) {
        super.initClicks()
        flashCardCv.setOnClickListener {
            findNavController().navigateWithAnimation(
                NavMainDirections.toFlashCardsLearningFragment()
            )
        }
    }
}