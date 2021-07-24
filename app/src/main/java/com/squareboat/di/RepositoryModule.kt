package com.squareboat.di

import com.squareboat.model.repository.local.UserRepository
import com.squareboat.model.repository.remote.AuthorizationRepository
import org.koin.dsl.module



val repositoryModule = module {

    single {
        AuthorizationRepository(get())
    }

    single{
        UserRepository(get())
    }

}
