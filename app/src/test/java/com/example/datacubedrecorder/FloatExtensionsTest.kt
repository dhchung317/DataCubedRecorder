package com.example.datacubedrecorder

import com.example.datacubedrecorder.common.extensions.formatDuration
import org.junit.Assert.assertEquals
import org.junit.Test

class FloatExtensionsTest {
    @Test
    fun `assert that one-hundred-eighty float displays three minutes and zero seconds `() {
        val expected = "3:00"
        val actual = 180f.formatDuration()

        assertEquals(expected, actual)
    }
}
//TODO other tests