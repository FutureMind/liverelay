package com.futuremind.liverelay

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * [LiveEventRelay] can be used in MVVM for the View to observe events coming from ViewModel.
 *
 * The events passed to [LiveEventRelay] are volatile, which means that they are only passed to
 * existing subscribers. When a new observer subscribes it is not passed previous states
 * (in contrast to [LiveStateRelay] which will repeat its last state for consecutive subscribers).
 * Such behaviour may be desirable for volatile events, like an error Toast being shown - which
 * should not be shown again after observer resubscribes e.g. after configuration change.
 * Internally this is because it is based on [PublishRelay].
 *
 * Subscribers don't need to worry about unexpected onComplete or onError, it only passes onNext events.
 * Also, once observe is passed a valid [LifecycleOwner], subscribers don't need to worry about unsubscribing.
 *
 */
class LiveEventRelay<T> : BaseLiveRelay<T>() {

    override val relay: Relay<T> = PublishRelay.create<T>().toSerialized()

}