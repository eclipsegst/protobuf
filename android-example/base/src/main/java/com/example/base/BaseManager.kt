package com.example.base

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import okhttp3.OkHttpClient
import android.os.Looper
import android.util.Log
import com.example.proto.GithubApi
import com.google.gson.JsonParser
import com.google.protobuf.Internal
import com.google.protobuf.util.JsonFormat
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
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

    fun request(url: String, callback: CompletionWithResult<GithubApi, Exception>) {
        backgroundHandler.post{
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use {
                    response ->

                val githubApi = GithubApi.newBuilder()
                JsonFormat.parser().merge(response.body?.string(), githubApi)

                mainHandler.post{
                    callback.invoke(Result.Success(githubApi.build()))
                }
            }
        }
    }
}