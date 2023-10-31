package com.andriivanov.gitusers.data.mapper

import com.andriivanov.gitusers.data.common.DataState

interface IMapper<IN, OUT> {

    fun map(serv: IN?): OUT

    fun mapListData(serv: DataState<List<IN>>): DataState<List<OUT>> {
        return when (serv) {
            is DataState.Loading -> DataState.Loading()
            is DataState.Failure -> DataState.Failure(serv.error)
            is DataState.Success -> {
                val mapped = serv.result.map { map(it) }
                DataState.Success(mapped)
            }
        }
    }
}