package com.squareboat.view.fragments.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentSplashBinding
import com.squareboat.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("ResourceType")
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun getLayoutRes() = R.layout.fragment_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateStatusBarColor(R.drawable.ic_square_boat, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            gotoNextFragment()
        }, 1500)
    }

    private fun gotoNextFragment() {
        if (viewModel.getUser() != null) {
            if(viewModel.getUser()!!.userRole == 0){
                findNavController().navigate(R.id.action_splashFragment_to_recruiterhomeFragment)

            }else{
                findNavController().navigate(R.id.action_splashFragment_to_candidateHomeFragment)
            }
        }else {
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
        }
    }
}