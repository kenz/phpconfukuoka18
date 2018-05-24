package org.firespeed.phpconfukuoka18.api

import io.reactivex.functions.Function
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JsoupMapper : Function<String, Document> {
    override fun apply(t: String): Document = Jsoup.parse(t)
}