package org.firespeed.phpconfukuoka18.api

import io.reactivex.functions.Function
import org.firespeed.phpconfukuoka18.model.Session
import org.jsoup.nodes.Document
import kotlin.collections.ArrayList

class ContentMapper : Function<Document, List<Session>> {
    override fun apply(response: Document): List<Session> {

        val sessionList = ArrayList<Session>()
        val rowList = response.select("#timetable tr")
        val roomList = ArrayList<String>()

        var timeTableSort = 0
        var id = 0L
        rowList.forEachIndexed { rowIndex, row ->
            if (rowIndex == 0) {
                row.select("th").drop(1).forEach{
                    roomList.add( it.text())
                }
            } else {

                var timeTable: String? = null
                row.select("td").forEachIndexed { index, element ->
                    if (index == 0) {
                        timeTable = element.text()
                        timeTableSort++
                    } else {
                        val dl = element.select("dl")
                        if (dl.isEmpty()) {
                            val session = Session(id = id++, timeTable = timeTable
                                    ?: "", timeTableSort = timeTableSort, location = null, title = element.text(), speaker = null, body = null, twitter = null, intermediate = null, favorite = false)
                            sessionList.add(session)
                        } else {
                            val location = roomList[index-1]
                            val title = dl.select(".title").text()
                            val body = dl.select(".body").text()
                            val speakerElement = dl.select(".speaker a")
                            val twitter = speakerElement.attr("href")
                            val speakerName = speakerElement.text()
                            val intermediateElement = dl.select(".title .intermediate")
                            val intermediate: String?
                            if (intermediateElement.isEmpty()) {
                                intermediate = null
                            } else {
                                intermediate = intermediateElement.text()
                                title.replace(intermediateElement.text(), "")
                            }


                            val session = Session(id = id++, timeTable = timeTable
                                    ?: "", timeTableSort = timeTableSort, location = location, title = title, speaker = speakerName, body = body, twitter = twitter, intermediate = intermediate, favorite = false)
                            sessionList.add(session)
                        }


                    }
                }
            }

        }

        return sessionList

    }
}


