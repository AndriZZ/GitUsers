package com.andriivanov.gitusers.di

import com.andriivanov.gitusers.viewmodel.GitUsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { GitUsersViewModel(get()) }

}