package edu.alatoo.kyrgyzlearning.presentation.ui.learning

import com.google.android.material.tabs.TabLayoutMediator
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentLearningBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.adapter.PagerAdapter
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.flashcards.FlashCardsFragment
import edu.alatoo.kyrgyzlearning.presentation.ui.learning.quizes.QuizzesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearningFragment : BaseFragment<LearningViewModel, FragmentLearningBinding>(
    FragmentLearningBinding::inflate
) {

    override val viewModel: LearningViewModel by viewModel()


    private lateinit var pagerAdapter: PagerAdapter

    override fun initialize() {
        super.initialize()
        initToolbar()
        initAdapter()
        initTabIcons()
    }

    override fun initClicks() {
        super.initClicks()
    }

    override fun observeViewModel() {
        super.observeViewModel()
    }

    private fun initAdapter() = with(binding) {
        pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        pagerAdapter.addFragment(FlashCardsFragment(), getString(R.string.flash_cards))
        pagerAdapter.addFragment(QuizzesFragment(), getString(R.string.quiz_list))
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTabTitle(position)
        }.attach()
    }

    private fun initTabIcons() {
        val icons = listOf(
            R.drawable.ic_flash_card,
            R.drawable.ic_quiz
        )
        binding.tabLayout.apply {
            for (i in 0..tabCount) {
                getTabAt(i)?.setIcon(icons[i])
            }
        }
    }


    private fun initToolbar() = with(binding) {
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Learning"
        }
    }

}