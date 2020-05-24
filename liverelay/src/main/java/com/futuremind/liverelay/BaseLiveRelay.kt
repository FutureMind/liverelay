package com.futuremind.liverelay

import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.Relay
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy.LATEST
import io.reactivex.rxjava3.core.Flowable


abstract class BaseLiveRelay<T> {

    protected abstract val relay: Relay<T>

    fun observe(lifecycleOwner: LifecycleOwner): Flowable<T> = relay
            .observeOn(AndroidSchedulers.mainThread())
            .bindToLifecycle(lifecycleOwner).toFlowable(LATEST)

    fun onNext(next: T) = relay.accept(next)

    @Deprecated(
            message = "Use onNext instead",
            replaceWith = ReplaceWith("onNext(state)")
    )
    fun nextState(state: T) = onNext(state)

}