package com.futuremind.liverelay

import androidx.lifecycle.Lifecycle.Event.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveEventRelayTest {

    @get:Rule
    val schedulersRule = SchedulersRule()

    private val lifecycleOwner = MockLifecycleOwner()
    private lateinit var relay: LiveEventRelay<Unit>

    @Before
    fun setUp(){
        relay = LiveEventRelay()
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
    fun `Should not pass any value from before it was subscribed`() {
        relay.onNext(Unit)
        relay.onNext(Unit)
        val testSubscriber = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber.assertValues(Unit, Unit)
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
    fun `Should not pass any value from before it was subscribed regardless of whether there is another subscriber`() {
        val testSubscriber1 = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        val testSubscriber2 = relay.observe(lifecycleOwner).test()
        relay.onNext(Unit)
        relay.onNext(Unit)
        testSubscriber1.assertValues(Unit, Unit, Unit)
        testSubscriber2.assertValues(Unit, Unit)
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