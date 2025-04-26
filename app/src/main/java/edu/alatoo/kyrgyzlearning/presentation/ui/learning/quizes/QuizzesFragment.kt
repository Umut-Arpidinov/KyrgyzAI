package edu.alatoo.kyrgyzlearning.presentation.ui.learning.quizes

import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentQuizListBinding
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.LearningViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizzesFragment : BaseFragment<LearningViewModel,FragmentQuizListBinding>(
    FragmentQuizListBinding::inflate
) {
    override val viewModel: LearningViewModel by viewModel()

}