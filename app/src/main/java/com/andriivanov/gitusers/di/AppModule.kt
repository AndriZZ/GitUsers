package com.andriivanov.gitusers.di

import com.andriivanov.gitusers.data.mapper.UserDetailsMapper
import com.andriivanov.gitusers.data.mapper.UserItemMapper
import org.koin.dsl.module

val appModule = module {
    factory { UserItemMapper() }
    factory { UserDetailsMapper() }
}