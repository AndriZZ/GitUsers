package com.andriivanov.gitusers.di

import com.andriivanov.gitusers.repository.GitUsersRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { GitUsersRepository(get(),get(),get()) }

}