package com.squareboat.di


import com.squareboat.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        SplashViewModel(get())

    }

    viewModel {
        SignUpViewModel(get(),get())
    }
    viewModel {
        LoginViewModel(get(),get())
    }

    viewModel {
        ForgotPasswordViewModel(get(),get())
    }

    viewModel {
        RecruiterViewModel(get())
    }

    viewModel {
        AddJobViewModel(get())
    }
    viewModel {
        CandidateViewModel(get())
    }
    viewModel {
        AppliedJobViewModel(get())
    }
    viewModel {
        ChangePasswordViewModel(get())
    }
}
