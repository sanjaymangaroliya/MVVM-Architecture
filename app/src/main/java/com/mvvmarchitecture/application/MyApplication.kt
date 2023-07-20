package com.mvvmarchitecture.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}