package com.futuremind.liverelay

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry


class MockLifecycleOwner : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle() = lifecycleRegistry

}