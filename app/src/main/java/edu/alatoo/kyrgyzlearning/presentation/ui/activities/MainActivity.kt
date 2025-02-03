package edu.alatoo.kyrgyzlearning.presentation.ui.activities

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import edu.alatoo.kyrgyzlearning.NavWelcomeDirections
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseActivity
import edu.alatoo.kyrgyzlearning.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate
){

    override val viewModel: MainViewModel by viewModel()

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHost
        navController = navHostFragment.navController
    }


    override fun observe() {
        super.observe()
        viewModel.isFirstLaunch.observe(this) {
            onFirstLaunch(it)
        }
    }

    private fun onFirstLaunch(isFirstLaunch: Boolean){
        when(isFirstLaunch) {
            true -> {
                navController.navigate(
                    NavWelcomeDirections.toNavFirstLaunch()
                )
            }

            else -> {
                navController.navigate(
                    NavWelcomeDirections.toNavMain()
                )
            }
        }

    }




}