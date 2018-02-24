package com.futuremind.liverelay

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * [LiveEventRelay] can be used to process states between ViewModel and LifecycleOwner.
 *
 * Subscribers don't need to worry about unexpected onComplete or onError, it only passes onNext events.
 * Also, once observe is passed a valid [LifecycleOwner], subscribers don't need to worry about unsubscribing.
 *
 * The states passed to [LiveEventRelay] are volatile, which means that they are only passed to
 * existing subscribers, when a new observer subscribes it is not passed previous states
 * (in contrast to [LiveRelay] which will repeat its last state for consecutive subscribers).
 * Such behaviour may be desirable for volatile events, like Toast being shown.
 * Internally this is because it is based on [PublishRelay].
 */
class LiveEventRelay<T> : BaseLiveRelay<T>() {

    override val relay: Relay<T> = PublishRelay.create<T>().toSerialized()

}