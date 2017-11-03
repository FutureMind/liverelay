package com.futuremind.liverelay

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.BehaviorRelay
import florent37.github.com.rxlifecycle.RxLifecycle
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import io.reactivex.ObservableTransformer

/**
 * LiveRelay can be used to process states between ViewModel and LifecycleOwner.
 *
 * Subscribers don't need to worry about unexpected onComplete or onError, it only passes
 * onNext events.
 * Also, once observe is passed a valid [LifecycleOwner], subscribers don't need to worry about unsubscribing.
 */
class LiveRelay<T>(private val lifecycleEvent: LifecycleEvent = LifecycleEvent.PAUSE) {

    private val relay = BehaviorRelay.create<T>().toSerialized()

    fun observe(lifecycleOwner: LifecycleOwner): Flowable<T> =
            relay.compose(lifecycleTransformer(lifecycleEvent, lifecycleOwner)).toFlowable(LATEST)

    fun nextState(state: T) = relay.accept(state)

    private fun lifecycleTransformer(lifecycleEvent: LifecycleEvent, lifecycleOwner: LifecycleOwner): ObservableTransformer<T, T> =
            when (lifecycleEvent) {
                LiveRelay.LifecycleEvent.PAUSE -> RxLifecycle.with(lifecycleOwner).disposeOnPause()
                LiveRelay.LifecycleEvent.STOP -> RxLifecycle.with(lifecycleOwner).disposeOnStop()
                LiveRelay.LifecycleEvent.DESTROY -> RxLifecycle.with(lifecycleOwner).disposeOnDestroy()
            }

    enum class LifecycleEvent {
        PAUSE,
        STOP,
        DESTROY
    }

}