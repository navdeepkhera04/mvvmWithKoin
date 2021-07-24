package com.squareboat.view.fragments.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentSignupBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.HandleClick
import com.squareboat.util.extensions.*
import com.squareboat.util.showError
import com.squareboat.util.showSuccess
import com.squareboat.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("ResourceType")
class SignupFragment : BaseFragment<FragmentSignupBinding>(), HandleClick {

    override fun getLayoutRes() = R.layout.fragment_signup
    private val isValid: Boolean
        get() {
            when {
                binding.viewModel!!.loginData.value!!.name.isNullOrEmpty() -> {
                    binding.firstNameET.error = baseActivity.getString(R.string.plz_enter_last_name)
                    binding.firstNameET.requestFocus()
                }
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
                binding.viewModel!!.loginData.value!!.confirmPassword.isNullOrEmpty() -> {
                    binding.confirmPasswordET.error = baseActivity.getString(R.string.plz_enter_confirm_password)
                    binding.confirmPasswordET.requestFocus()
                }

                !binding.viewModel!!.loginData.value!!.password!!.isValidPassword() -> {
                    binding.passwordET.error =
                        baseActivity.getString(R.string.plz_enter_valid_password)
                    binding.passwordET.requestFocus()
                }

                !binding.viewModel!!.loginData.value!!.password!!.ismatchPassword(binding.viewModel!!.loginData.value!!.confirmPassword!!) -> {
                    binding.confirmPasswordET.error =
                        baseActivity.getString(R.string.password_missmatch)
                    binding.confirmPasswordET.requestFocus()
                }

                binding.viewModel!!.loginData.value!!.skills.isNullOrEmpty() -> {
                    binding.skillsET.error = baseActivity.getString(R.string.select_skills)
                    binding.skillsET.requestFocus()
                }
                else -> {

                    return true
                }
            }

            return false
        }

    private val viewModel by viewModel<SignUpViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initBinding()
        addObserver()
    }

    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, true, R.color.logoImageColor)
    }

    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.haveAccountTV.setSpanString(binding.haveAccountTV.checkString(), start = 25,
            showBold = true, color = R.color.app_color, onSpanClick = {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            })
    }

    override fun onClickHandle(view: View) {
        when(view.id) {
            R.id.nextBT -> {
                if(isValid) {
                    hitApiRegisterUser()
                }
            }

            R.id.businessAccountCB -> {
                handleBusinessAccountCheckBox()
            }
        }
    }

    private fun handleBusinessAccountCheckBox() {
        binding.viewModel!!.loginData.value!!.isRecruiterAccount =
            binding.businessAccountCB.isChecked

        if(binding.viewModel!!.loginData.value!!.isRecruiterAccount) {
            binding.viewModel!!.loginData.value!!.userRole = 0
        } else {
            binding.viewModel!!.loginData.value!!.userRole = 1
        }
    }

    private fun hitApiRegisterUser() {
        viewModel.hitApiRegisterUser()
    }


    private fun addObserver() {
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer {
            hideLoading()
            it.getContentIfNotHandled()?.let {
                when(it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data=it.data.data
                        if(it.data.success!!){
                            viewModel.saveUser(data!!)
                            viewModel.saveToken(data.token!!)
                            if(it.data.data.userRole == 0){
                                showSuccess(requireContext(),"Recruiter Login")
                                findNavController().navigate(R.id.action_signupFragment_to_recruiterhomeFragment)
                            }else{
                                showSuccess(requireContext(),"Candidate Login")
                                findNavController().navigate(R.id.action_splashFragment_to_candidateHomeFragment)
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
