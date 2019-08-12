package com.example.android_starter.base

import android.app.Application
import com.example.base.BaseManager

/**
 *  Created by Zhaolong Zhong on 08/11/2019.
 */

class MyApplication: Application() {

    lateinit var baseManager: BaseManager

    override fun onCreate() {
        super.onCreate()
        baseManager = BaseManager(this)

        init()
    }

    private fun init() {
        // Stub
    }
}