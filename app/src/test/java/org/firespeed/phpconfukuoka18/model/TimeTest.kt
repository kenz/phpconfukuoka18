package org.firespeed.phpconfukuoka18.model

import org.junit.Assert
import org.junit.Test

class TimeTest {

    @Test
    fun standardTest() {
        val time = Time("12:34")
        Assert.assertEquals(time.getHHMM(), 1234)
        Assert.assertEquals(time.label, "12:34")

    }

    @Test
    fun fromToTest() {
        val time = Time("12:34〜22:30")
        Assert.assertEquals(time.getHHMM(), 1234)
        Assert.assertEquals(time.label, "12:34〜22:30")

    }

    @Test
    fun nullTest() {
        val time = Time("hoge")
        Assert.assertNull(time)

    }

}