package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.wordDetail

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.databinding.FragmentWordDetailBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.show
import edu.alatoo.kyrgyzlearning.presentation.extensions.showSimpleDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class WordDetailFragment : BaseFragment<WordDetailViewModel, FragmentWordDetailBinding>(
    FragmentWordDetailBinding::inflate
) {

    override val viewModel: WordDetailViewModel by viewModel()

    private val args: WordDetailFragmentArgs by navArgs()

    override fun initialize() {
        super.initialize()
        viewModel.getWordById(args.wordId)
        initToolbar()
    }

    override fun initClicks() = with(binding){
        super.initClicks()
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.word.observe(viewLifecycleOwner) {
            onWordReceived(it)
        }

        viewModel.translations.observe(viewLifecycleOwner) {
            it?.let {
                onTranslationsReceived(it)
            }
        }

        viewModel.examples.observe(viewLifecycleOwner) {
            it?.let {
                onExamplesReceived(it)
            }
        }


    }


    private fun onTranslationsReceived(translations: String) = with(binding) {
        tvTranslationPlaceholder.text = translations
    }

    private fun onExamplesReceived(examples: String) = with(binding) {
        tvExamplePlaceholder.text = examples
    }


    private fun onWordReceived(word: Word) = with(binding) {
        tvWord.text = word.word
    }


    override fun onLoading(loading: Boolean) {
        super.onLoading(loading)
        binding.progress.isVisible = loading
    }

    override fun onError(message: String) {
        super.onError(message)
        showSimpleDialog(message){}
    }



    private fun initToolbar() = with(binding) {
        toolbar.apply {
            toolbarTitle.text = "Word detail"
            ivHelp.run {
                show()
                setOnClickListener {
                    showSimpleDialog(R.string.vertex_feature_info){}
                }
            }
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


}