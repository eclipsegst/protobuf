package com.example.base

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import okhttp3.OkHttpClient
import android.os.Looper
import okhttp3.Request
import java.lang.Exception


/**
 *  Created by Zhaolong Zhong on 08/11/2019.
 */

class BaseManager(applicationContext: Context) {

    private val handlerThread = HandlerThread("base_manager_background_thread")
    private var backgroundHandler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    init {
        handlerThread.start()
        backgroundHandler = Handler(handlerThread.looper)
    }

    fun deinit() {
        handlerThread.quitSafely()
    }

    fun request(url: String, callback: CompletionWithResult<String, Exception>) {
        backgroundHandler.post{
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use {
                    response ->
                val result = response.body?.string() ?: "empty response"
                mainHandler.post{
                    callback.invoke(Result.Success(result))
                }
            }
        }
    }
}