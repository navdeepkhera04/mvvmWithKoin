package com.squareboat.view.fragments.authentication

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentForgotPasswordBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.*
import com.squareboat.util.extensions.isValidEmail
import com.squareboat.viewmodel.ForgotPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(), HandleClick {
    private val viewModel by viewModel<ForgotPasswordViewModel>()
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

                else -> {

                    return true
                }
            }

            return false
        }


    override fun getLayoutRes() = R.layout.fragment_forgot_password

    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initBinding()
        addObserver()
    }

    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onClickHandle(view: View) {

        when(view.id) {
            R.id.sendBT -> {
                if(isValid){
                    hitApiForgotPassword()
                }
            }
            R.id.backIV -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun hitApiForgotPassword() {
        viewModel.hitApiForgotPassword()
    }


    private fun addObserver() {
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer {
            hideLoading()
            it.getContentIfNotHandled().let {
                when(it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data = it.data.data
                        if(it.data.success!!){
                            viewModel.saveToken(data!!.token!!)
                            findNavController().navigate(R.id.action_forgotFragment_to_changePaqsswordFragment)
                            showSuccess(requireContext(), "Token value : "+data!!.token)
                        }
                    }
                    is DataResult.Failure -> {
                        it.message.let { it1 -> showError(requireContext(), it1!!) }
                    }
                }

            }
        })
    }
}