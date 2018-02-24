package com.futuremind.liverelay

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

}