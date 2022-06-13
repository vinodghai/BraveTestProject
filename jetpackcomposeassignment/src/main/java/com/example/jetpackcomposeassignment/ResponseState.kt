package com.example.jetpackcomposeassignment

import com.example.jetpackcomposeassignment.btcassetlist.error.BraveError

sealed class ResponseState<T> {
    class INITIAL<T> : ResponseState<T>()
    class LOADING<T> : ResponseState<T>()
    class FAILURE<T>(val error: BraveError) : ResponseState<T>()
    class SUCCESS<T>(val response: T) : ResponseState<T>()
}
