package com.futuremind.liverelay

import android.arch.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay

/**
 * [LiveStateRelay] can be used in MVVM for the View to observe states coming from ViewModel.
 *
 * When a new observer subscribes to [LiveStateRelay] it will be passed the last state if it exists
 * (in contrast to [LiveEventRelay] which will only pass events registered after subscribing). This
 * is desirable for application states, like results list being passed to the View - this way after
 * resubscribing after e.g. configuration change the state will be recreated. Internally this is
 * because it is based on [BehaviorRelay].
 *
 * Subscribers don't need to worry about unexpected onComplete or onError, it only passes onNext events.
 * Also, once [observe] is passed a valid [LifecycleOwner], subscribers don't need to worry about unsubscribing.
 *
 */
class LiveStateRelay<T> : BaseLiveRelay<T>() {

    override val relay: Relay<T> = BehaviorRelay.create<T>().toSerialized()

}