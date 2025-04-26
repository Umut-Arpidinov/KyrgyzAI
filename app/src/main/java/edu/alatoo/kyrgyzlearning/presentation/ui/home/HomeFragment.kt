package edu.alatoo.kyrgyzlearning.presentation.ui.home

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.NavMainDirections
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.databinding.FragmentHomeBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.navigateWithAnimation
import edu.alatoo.kyrgyzlearning.presentation.extensions.show
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.DictionaryFragmentDirections
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.adapter.DictionaryAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModel()

    private val adapter = DictionaryAdapter()


    override fun initialize() {
        super.initialize()
        initToolbar()

    }

    override fun onLoading(loading: Boolean) {
        super.onLoading(loading)
        binding.progress.isVisible = loading
    }

    override fun initClicks() = with(binding) {
        super.initClicks()

        adapter.onWordListener = {
            it.id?.let { wordId ->
               findNavController().navigateWithAnimation(NavMainDirections.toWordDetailScreen(wordId))
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.words.observe(viewLifecycleOwner) {
            onWordsReceived(it)
        }
        viewModel.wordCount.observe(viewLifecycleOwner) {
            onWordCountReceived(it)
        }
        viewModel.wordOfDay.observe(viewLifecycleOwner) {
            onWordOfDayReceived(it)
        }
    }

    private fun onWordsReceived(words: List<Word>) = with(binding) {
        adapter.submitList(words)
        rvWords.adapter = adapter
    }
    private fun onWordCountReceived(count: Int) = with(binding) {
        objectsCount.text = count.toString()
        viewModel.getWordOfDay()
    }
    private fun onWordOfDayReceived(word: Word?) = with(binding){
        if (word != null) {
            llWordOfTheDay.show()
            tvWordOfDay.text = word.word
        }
        else {
            llWordOfTheDay.hide()
        }
    }


    private fun initToolbar() = with(binding) {
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Home"
        }
    }


}