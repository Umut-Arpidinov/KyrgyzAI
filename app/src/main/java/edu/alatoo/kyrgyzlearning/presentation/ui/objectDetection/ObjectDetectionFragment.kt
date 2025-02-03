package edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection

import androidx.fragment.app.viewModels
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentObjectDetectionBinding

class ObjectDetectionFragment : BaseFragment<ObjectDetectionViewModel, FragmentObjectDetectionBinding>(
    FragmentObjectDetectionBinding::inflate
) {


    override val viewModel: ObjectDetectionViewModel by viewModels()


}