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
        var id = 1
        rowList.forEachIndexed { rowIndex, row ->
            if (rowIndex == 0) {
                row.select("th").drop(1).forEach {
                    roomList.add(it.text())
                }
            } else {

                var timeTable: String? = null
                row.select("td").forEachIndexed { index, element ->
                    if (index == 0) {
                        if (element.`is`(".time")) {
                            timeTable = element.text()
                            timeTableSort++
                        }
                        val dl = element.select("dl")
                        if (dl.isNotEmpty()) {
                            // 17時の部
                            val splitText = dl.select("dt").text().split("】")
                            val location: String
                            if (splitText.size > 1) {
                                location = splitText[0].replace("【", "")
                                if(timeTable!=splitText[1]) {
                                    timeTable = splitText[1].split("〜")[0]
                                    timeTableSort++
                                }
                            } else {
                                location = roomList[1]
                            }
                            var title = dl.select(".title").text()
                            val body = dl.select(".body").text()
                            val speakerElement = dl.select(".speaker a")
                            val twitter = speakerElement.attr("href")
                            val speakerName = speakerElement.text()
                            val levelElement = dl.select(".title span")
                            val level: String?
                            if (levelElement.isEmpty()) {
                                level= null
                            } else {
                                level= levelElement.text()
                                title = title.replace(levelElement.text(), "")
                            }


                            val session = Session(id = id++, timeTable = timeTable
                                    ?: "", timeTableSort = timeTableSort, location = location, title = title, speaker = speakerName, body = body, twitter = twitter, level = level, favorite = false)
                            sessionList.add(session)


                        }

                    } else {
                        val dl = element.select("dl")
                        if (dl.isEmpty()) {
                            if (element.text() != "ー") {
                                val session = Session(id = id++, timeTable = timeTable
                                        ?: "", timeTableSort = timeTableSort, location = null, title = element.text(), speaker = null, body = null, twitter = null, level = null, favorite = false)
                                sessionList.add(session)
                            }
                        } else if (dl.size > 1) {
                            // LT大会
                            val location = roomList[index - 1]
                            val splitText = dl.select("dt").text().split(" ")
                            val title = if (splitText.size > 1) {
                                splitText[1]
                            } else {
                                splitText[0]
                            }
                            val bodyBuilder = StringBuilder()
                            dl.map {
                                bodyBuilder.appendln(it.select(".title").text())
                                bodyBuilder.appendln(it.select(".speaker").text())
                                bodyBuilder.append("\r\n")
                            }
                            val body = bodyBuilder.toString()

                            val session = Session(id = id++, timeTable = timeTable
                                    ?: "", timeTableSort = timeTableSort, location = location, title = title, speaker = null, body = body, twitter = null, level= null, favorite = false)
                            sessionList.add(session)
                        } else {
                            val location = roomList[index - 1]
                            var title = dl.select(".title").text()
                            val body = dl.select(".body").text()
                            val speakerElement = dl.select(".speaker a")
                            val twitter = speakerElement.attr("href")
                            val speakerName = speakerElement.text()
                            val levelElement= dl.select(".title span")
                            val level: String?
                            if (levelElement.isEmpty()) {
                                level = null
                            } else {
                                level= levelElement.text()
                                title = title.replace(levelElement.text(), "")
                            }


                            val session = Session(id = id++, timeTable = timeTable
                                    ?: "", timeTableSort = timeTableSort, location = location, title = title, speaker = speakerName, body = body, twitter = twitter, level = level, favorite = false)
                            sessionList.add(session)
                        }


                    }
                }
            }

        }

        return sessionList

    }
}


