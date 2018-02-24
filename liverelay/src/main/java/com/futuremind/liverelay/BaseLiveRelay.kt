package com.futuremind.liverelay

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.Relay
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable


abstract class BaseLiveRelay<T> {

    protected abstract val relay: Relay<T>

    fun observe(lifecycleOwner: LifecycleOwner): Flowable<T> =
            relay.bindToLifecycle(lifecycleOwner).toFlowable(LATEST)

    fun onNext(next: T) = relay.accept(next)

    @Deprecated(
            message = "Use onNext instead",
            replaceWith = ReplaceWith("onNext(state)")
    )
    fun nextState(state: T) = onNext(state)

}