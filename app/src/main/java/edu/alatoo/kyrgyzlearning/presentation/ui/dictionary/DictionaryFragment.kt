package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary

import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.databinding.FragmentDictionaryBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.setAdjustNothing
import edu.alatoo.kyrgyzlearning.presentation.extensions.showSimpleDialog
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.adapter.DictionaryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DictionaryFragment : BaseFragment<DictionaryViewModel, FragmentDictionaryBinding>(
    FragmentDictionaryBinding::inflate
) {

    override val viewModel: DictionaryViewModel by viewModel()

    private val adapter = DictionaryAdapter()


    override fun initialize() {
        super.initialize()
        setAdjustNothing()
        viewModel.getWordsFromDb()
        initToolbar()
    }

    override fun initClicks() {
        super.initClicks()
        adapter.onWordListener = {
            it.id?.let {
                findNavController().navigate(DictionaryFragmentDirections.toWordDetailScreen(it))
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.words.observe(viewLifecycleOwner) {
            onWordsReceived(it)
        }

    }

    private fun onWordsReceived(words: List<Word>) = with(binding) {
        adapter.submitList(words)
        rvWords.adapter = adapter
    }

    override fun onError(message: String) {
        super.onError(message)
        showSimpleDialog(message){}
    }


    private fun initToolbar() = with(binding) {
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Dictionary"
        }
    }


}