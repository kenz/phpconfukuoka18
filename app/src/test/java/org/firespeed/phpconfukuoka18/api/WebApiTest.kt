package org.firespeed.phpconfukuoka18.api

import org.jsoup.Jsoup
import org.junit.Test

import org.junit.Assert.*

class WebApiTest {

    @Test
    fun getContent() {
        val doc = Jsoup.connect("https://phpcon.fukuoka.jp/2018/").get()
        val cm = ContentMapper()
        val result = cm.apply(doc)

    }
}