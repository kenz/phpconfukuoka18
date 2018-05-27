package org.firespeed.phpconfukuoka18.api

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class WebApiTest {

    @Test
    fun getContent() {
        val doc = Jsoup.connect("https://phpcon.fukuoka.jp/2018/").get()
        val cm = ContentMapper()
        val result = cm.apply(doc)
        assertEquals(result.size, 35)
        assertEquals(result[0].id, 1)
        assertNull(result[0].location)
        assertEquals(result[0].timeTable, "9:30")
        assertEquals(result[0].title, "受付開始")
        assertNull(result[0].body)
        assertNull(result[0].speaker)
        assertNull(result[0].twitter)
        assertNull(result[0].level)
        assertEquals(result[0].favorite, false)

        assertEquals(result[2].id, 3)
        assertEquals(result[2].location, "Fusicホール")
        assertEquals(result[2].timeTable, "10:30")
        assertEquals(result[2].timeTableSort, 3)
        assertEquals(result[2].title, "何故PHPなんですか？")
        assertEquals(result[2].body, "セッション内容： 皆様PHP好きですか？ 私はPHPを触り続けてきました。 PHPと一緒にエンジニア人生を歩んできたと行っても過言ではありません。 そんな私がホントにこのままPHP書き続けて良いのか不安になることがありました。 この不安を解消する方法としてもう一つの選択肢として golang を本格的に導入してみました。 利用して見えてきたPHPの弱点とPHPの長所を実感しました。 何故PHPなのか？その答えを一緒に再考してみませんか？ 私はPHPが大好きです。")
        assertEquals(result[2].speaker, "清家史郎（@seike460）")
        assertEquals(result[2].twitter, "https://twitter.com/seike460")
        assertEquals(result[2].level, "初級 - For Beginner")
        assertEquals(result[2].favorite, false)


    }
}