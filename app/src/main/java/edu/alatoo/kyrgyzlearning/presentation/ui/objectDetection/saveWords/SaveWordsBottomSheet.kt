package edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.saveWords

import androidx.fragment.app.FragmentManager
import edu.alatoo.kyrgyzlearning.common.base.BaseBottomSheet
import edu.alatoo.kyrgyzlearning.common.base.BottomSheetType
import edu.alatoo.kyrgyzlearning.databinding.BottomSheetSaveWordsBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.showSimpleDialog
import edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.ObjectDetectionViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SaveWordsBottomSheet : BaseBottomSheet<BottomSheetSaveWordsBinding>(
    BottomSheetSaveWordsBinding::inflate
) {

    override fun bottomSheetType(): BottomSheetType = BottomSheetType.WRAP_CONTENT

    private val viewModel: ObjectDetectionViewModel by activityViewModel()

    private val wordsAdapter by lazy {
        SaveWordsAdapter()
    }


    override fun initialize() {
        super.initialize()
    }

    override fun initClicks() = with(binding) {
        super.initClicks()
        btnSave.setOnClickListener {
            val selectedWords = wordsAdapter.getSelectedWords() // Get selected words
            if (selectedWords.isNotEmpty()) {
                viewModel.saveWordsToDb(selectedWords){
                    dismiss()
                }

            }
        }
    }


    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.wordsToSave.observe(viewLifecycleOwner) {
            setUpRecycler(it)
        }
    }

    private fun setUpRecycler(words: List<String>) = with(binding) {
        wordsAdapter.submitList(words)
        rvWords.adapter = wordsAdapter
    }


    fun showBottomSheet(manager: FragmentManager) {
        if (!isAdded) {
            this.show(manager, null)
        }
    }


}

