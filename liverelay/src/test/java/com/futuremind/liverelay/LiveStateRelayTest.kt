package com.futuremind.liverelay

import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import org.junit.Before
import org.junit.Test

class LiveStateRelayTest {

    private val lifecycleOwner = MockLifecycleOwner()
    private lateinit var relay: LiveStateRelay<Unit>

    @Before
    fun setUp(){
        relay = LiveStateRelay()
    }

    @Test
    fun `Given no values should pass nothing`() {
        val testSubscriber = relay.observe(lifecycleOwner).test()
        testSubscriber.assertNoValues()
    }

    @Test
    fun `Given a value should pass it to observer`() {
        val testSubscriber = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        testSubscriber.assertValues(Unit)
    }

    @Test
    fun `Given 3 values should pass all of them to the observer`() {
        val testSubscriber = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber.assertValues(Unit, Unit, Unit)
    }

    @Test
    fun `Should pass a single value from before it was subscribed`() {
        relay.onNext(Unit) //this should not be passed
        relay.onNext(Unit)
        val testSubscriber = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber.assertValues(Unit, Unit, Unit)
    }

    @Test
    fun `Should pass values to all subscribers`() {
        val testSubscriber1 = relay.observe(lifecycleOwner).test()
        val testSubscriber2 = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber1.assertValues(Unit, Unit)
        testSubscriber2.assertValues(Unit, Unit)
    }

    @Test
    fun `Should pass a single value from before it was subscribed when there is more than one subscriber`() {
        val testSubscriber1 = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        val testSubscriber2 = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber1.assertValues(Unit, Unit, Unit)
        testSubscriber2.assertValues(Unit, Unit, Unit)
    }

    @Test
    fun `Should stop observing onStop when started onStart`() {
        lifecycleOwner.setState(ON_CREATE)
        lifecycleOwner.setState(ON_START)
        val testSubscriber = relay.observe(lifecycleOwner).test()
        lifecycleOwner.setState(ON_RESUME)
        relay.onNext(Unit)
        lifecycleOwner.setState(ON_PAUSE)
        lifecycleOwner.setState(ON_STOP)
        relay.onNext(Unit)
        lifecycleOwner.setState(ON_DESTROY)
        testSubscriber.assertValues(Unit)
    }

    @Test
    fun `Should stop observing onDestroy when started onCreate`() {
        lifecycleOwner.setState(ON_CREATE)
        val testSubscriber = relay.observe(lifecycleOwner).test()
        lifecycleOwner.setState(ON_START)
        lifecycleOwner.setState(ON_RESUME)
        relay.onNext(Unit)
        lifecycleOwner.setState(ON_PAUSE)
        lifecycleOwner.setState(ON_STOP)
        relay.onNext(Unit)
        lifecycleOwner.setState(ON_DESTROY)
        relay.onNext(Unit)
        testSubscriber.assertValues(Unit, Unit)
    }

}