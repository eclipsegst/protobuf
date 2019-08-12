package com.example.android_starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android_starter.base.MyApplication
import com.example.base.Result

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendSimpleRequest()
    }

    private fun sendSimpleRequest() {

        Log.d(TAG, "sending simple request")

        val baseManager = (application as MyApplication).baseManager
        // You can also find the response by using terminal, type:
        // curl https://api.github.com
        baseManager.request("https://api.github.com") {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "response: ${it.response}")
                }
                is Result.Failure -> Log.d(TAG, "error: ${it.error.message}")
            }
        }
    }
}
