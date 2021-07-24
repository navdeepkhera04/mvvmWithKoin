package com.squareboat.view.fragments.authentication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentLoginSignupBinding
import com.squareboat.util.HandleClick
import com.squareboat.util.extensions.checkString
import com.squareboat.util.extensions.getLength
import com.squareboat.util.extensions.setSpanString
import com.squareboat.util.showInfo


@SuppressLint("ResourceType")
class LoginSignupFragment  : BaseFragment<FragmentLoginSignupBinding>(), HandleClick {

    override fun getLayoutRes() = R.layout.fragment_login_signup


    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.drawable.ic_square_boat, true,R.color.blackTransparent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.handleClick = this

        binding.termConditionsTV.setSpanString(binding.termConditionsTV.checkString(),35
            , 48,50, binding.termConditionsTV.getLength(),
            showBold = true, color = R.color.app_color, onSpanClick = {

                showInfo(requireContext(),"Not Implemented Yet")
            })

    }

    override fun onClickHandle(view: View) {

        when(view.id) {
            R.id.loginSBT -> {
                findNavController().navigate(R.id.action_loginSignupFragment_to_loginFragment)
            }

            R.id.signUpBT -> {
                findNavController().navigate(R.id.action_loginSignupFragment_to_signupFragment)
            }
        }
    }
}