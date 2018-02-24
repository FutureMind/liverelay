package com.futuremind.liverelay

import android.arch.lifecycle.Lifecycle.Event
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry


class MockLifecycleOwner : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle() = lifecycleRegistry

    fun setState(state: Event) = lifecycleRegistry.handleLifecycleEvent(state)

}