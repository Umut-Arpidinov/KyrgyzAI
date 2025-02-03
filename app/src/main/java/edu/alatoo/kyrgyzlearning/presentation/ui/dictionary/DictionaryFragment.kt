package edu.alatoo.kyrgyzlearning.presentation.ui.dictionary

import androidx.fragment.app.viewModels
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentDictionaryBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.setAdjustNothing
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.adapter.DictionaryAdapter

class DictionaryFragment : BaseFragment<DictionaryViewModel, FragmentDictionaryBinding>(
    FragmentDictionaryBinding::inflate
) {

    override val viewModel: DictionaryViewModel by viewModels()

    private val adapter = DictionaryAdapter()


    override fun initialize() {
        super.initialize()
        setAdjustNothing()
        initToolbar()
    }

    override fun initClicks() {
        super.initClicks()
    }

    override fun observeViewModel() {
        super.observeViewModel()
    }


    private fun initToolbar() = with(binding) {
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Dictionary"
        }
    }


}