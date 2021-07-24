package com.squareboat.view.fragments.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentLoginBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.HandleClick
import com.squareboat.util.extensions.checkString
import com.squareboat.util.extensions.isValidEmail
import com.squareboat.util.extensions.setSpanString
import com.squareboat.util.showError
import com.squareboat.util.showSuccess
import com.squareboat.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(), HandleClick {

    override fun getLayoutRes() = R.layout.fragment_login
    private val viewModel by viewModel<LoginViewModel>()
    private val isValid: Boolean
        get() {
            when {

                binding.viewModel!!.loginData.value!!.email.isNullOrEmpty() -> {
                    binding.emailET.error = baseActivity.getString(R.string.plz_enter_email)
                    binding.emailET.requestFocus()
                }
                !binding.viewModel!!.loginData.value!!.email!!.isValidEmail() -> {
                    binding.emailET.error = baseActivity.getString(R.string.plz_enter_valid_email)
                    binding.emailET.requestFocus()
                }
                binding.viewModel!!.loginData.value!!.password.isNullOrEmpty() -> {
                    binding.passwordET.error = baseActivity.getString(R.string.plz_enter_password)
                    binding.passwordET.requestFocus()
                }

                else -> {

                    return true
                }
            }

            return false
        }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        addObserver()
    }

    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, true, R.color.logoImageColor)
    }

    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.dontHaveAccountTV.setSpanString(binding.dontHaveAccountTV.checkString(), start = 20,
            showBold = true, color = R.color.app_color, onSpanClick = {
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            })
    }

    override fun onClickHandle(view: View) {
        when(view.id) {
            R.id.loginBT -> {
                if(isValid){
                    hitApiLoginUser()
                }
            }
            R.id.forgotPasswordTV -> {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
        }
    }

    private fun hitApiLoginUser() {
        viewModel.hitApiLoginUser()
    }


    private fun addObserver() {
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer { it ->
            hideLoading()
            it.getContentIfNotHandled()?.let {
                when(it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data = it.data.data
                        if(it.data.success!!){
                            viewModel.saveUser(data!!)
                            viewModel.saveToken(data.token!!)
                            if(it.data.data.userRole == 0){
                                showSuccess(requireContext(),"Recruiter Login")
                                findNavController().navigate(R.id.action_signupFragment_to_recruiterhomeFragment)
                            }else{
                                showSuccess(requireContext(),"Candidate Login")
                                findNavController().navigate(R.id._signupFragment_to_candidateHomeFragment)
                            }

                        }

                    }
                    is DataResult.Failure -> {
                        it.message?.let { it1 -> showError(requireContext(), it1) }
                    }
                }
            }
        })
    }
}
