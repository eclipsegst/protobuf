package com.example.base

/**
 *  Created by Zhaolong Zhong on 08/11/2019.
 */

sealed class Result<R, E: Exception> {
    class Success<R, E: Exception>(val response: R): Result<R, E>()
    class Failure<R, E: Exception>(val error: E): Result<R, E>()
}

typealias CompletionWithResult<R, E> = (Result<R, E>) -> Unit