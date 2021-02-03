package com.devstree.basesample.view.ui.base

import android.app.Application
import androidx.lifecycle.LifecycleObserver

class Controller : Application(), LifecycleObserver {
    private var duration: Long = -1

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Controller
        private val TAG = Controller::class.java.name
    }
}

