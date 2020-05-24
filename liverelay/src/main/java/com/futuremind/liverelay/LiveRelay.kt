package com.futuremind.liverelay

import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.Relay

/**
 * LiveRelay can be used to process states between ViewModel and LifecycleOwner.
 *
 * Subscribers don't need to worry about unexpected onComplete or onError, it only passes onNext events.
 * Also, once observe is passed a valid [LifecycleOwner], subscribers don't need to worry about unsubscribing.
 */
@Deprecated(
        message = "Replace with LiveStateRelay (which is now introduce to distinguish from LiveEventRelay)",
        replaceWith = ReplaceWith(
                "LiveStateRelay",
                "com.futuremind.liverelay.LiveStateRelay"
        )
)
class LiveRelay<T> : BaseLiveRelay<T>() {

    override val relay: Relay<T> = BehaviorRelay.create<T>().toSerialized()

}