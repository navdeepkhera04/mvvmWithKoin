package com.squareboat.viewmodel

import androidx.lifecycle.ViewModel
import com.squareboat.model.repository.local.UserRepository

class SplashViewModel(val userRepository: UserRepository): ViewModel() {

    fun getUser()= userRepository.getCurrentUser()
}