package com.andriivanov.gitusers

import android.app.Application
import com.andriivanov.gitusers.di.appModule
import com.andriivanov.gitusers.di.networkModule
import com.andriivanov.gitusers.di.repositoryModule
import com.andriivanov.gitusers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GitUsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // setup di
        startKoin {
            androidLogger()
            androidContext(this@GitUsersApplication)

            koin.loadModules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                )
            )
        }
    }
}