package com.andriivanov.gitusers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    /**
     * Launches given action in [Dispatchers.IO]
     */
    protected fun launchIO(action: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) { action.invoke() }

}