package com.futuremind.liverelay

import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.Relay
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers


abstract class BaseLiveRelay<T> {

    protected abstract val relay: Relay<T>

    fun observe(lifecycleOwner: LifecycleOwner): Flowable<T> = relay.observeOn(AndroidSchedulers.mainThread()).bindToLifecycle(lifecycleOwner).toFlowable(LATEST)

    fun onNext(next: T) = relay.accept(next)

    @Deprecated(
            message = "Use onNext instead",
            replaceWith = ReplaceWith("onNext(state)")
    )
    fun nextState(state: T) = onNext(state)

}