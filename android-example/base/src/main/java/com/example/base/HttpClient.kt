package com.example.base

import java.lang.Exception

/**
 *  Created by Zhaolong Zhong on 08/11/2019.
 */

interface SimpleCallback {
    fun onSuccess(response: String)
    fun onFailure(e: Exception)
}
