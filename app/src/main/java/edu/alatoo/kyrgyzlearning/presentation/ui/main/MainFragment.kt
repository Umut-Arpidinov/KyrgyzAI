package edu.alatoo.kyrgyzlearning.presentation.ui.main

import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import edu.alatoo.kyrgyzlearning.NavMainDirections
import edu.alatoo.kyrgyzlearning.NavWelcomeDirections
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentMainBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.gone
import edu.alatoo.kyrgyzlearning.presentation.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainFragmentViewModel, FragmentMainBinding>(
    FragmentMainBinding::inflate
) {

    override val viewModel: MainFragmentViewModel by viewModel()


    override fun initialize() {
        super.initialize()
        initNavigation()
    }

    override fun initClicks() = with(binding){
        super.initClicks()
        fabScan.setOnClickListener {
            findNavController().navigate(
                NavWelcomeDirections.toObjectDetectionFragment()
            )
        }
    }


    private fun initNavigation() {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id !in SHOW_BOTTOM_NAV_VIEW_LIST) {
                hideNavigationBar()
            } else {
                showNavigationBar()
            }

            if (destination.id !in SHOW_SCAN_BUTTON_LIST) {
                hideFloatingButton()
            } else {
                showFloatingButton()
            }
        }

        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun hideNavigationBar() {
        if (binding.bottomNavView.isVisible) {
            binding.bottomNavView.gone(true, 100)
        }
    }

    private fun hideFloatingButton() {
        if (binding.fabScan.isVisible) {
            binding.fabScan.gone(true, 100)
        }
    }
    private fun showFloatingButton() {
        if (!binding.fabScan.isVisible) {
            binding.fabScan.visible(true, 100)
        }
    }


    private fun showNavigationBar() {
        if (!binding.bottomNavView.isVisible) {
            binding.bottomNavView.visible(true)
        }
    }

    companion object {
        private val SHOW_BOTTOM_NAV_VIEW_LIST = setOf(
            R.id.dictionaryFragment,
            R.id.homeFragment,
            R.id.settingsFragment,
        )

        private val SHOW_SCAN_BUTTON_LIST = setOf(
            R.id.dictionaryFragment
        )
    }

}