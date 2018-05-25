package org.firespeed.phpconfukuoka18.model

import android.support.annotation.Nullable
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.firespeed.phpconfukuoka18.api.WebApi

@Table
class Session(

        @Setter("id")
        @PrimaryKey(autoincrement = true)
        @Column
        val id: Long,
        @Column
        @Setter("location")
        val location: String?,
        @Column
        @Setter("timeTable")
        val timeTable: String,
        @Column(indexed = true)
        @Setter("timeTableSort")
        val timeTableSort: Int,
        @Column
        @Setter("title")
        val title: String,
        @Column
        @Nullable
        @Setter("body")
        val body: String?,
        @Column
        @Nullable
        @Setter("speaker")
        val speaker: String?,
        @Column
        @Nullable
        @Setter("twitter")
        val twitter: String?,
        @Column(indexed = true)
        @Nullable
        @Setter("level")
        val level: String?,
        @Column(indexed = true)
        @Setter("favorite")
        var favorite: Boolean
) {
    fun requireUpdate(fromApi: Session): Boolean {
        if (location != fromApi.location) return true
        if (timeTable != fromApi.timeTable) return true
        if (timeTableSort != fromApi.timeTableSort) return true
        if (title != fromApi.title) return true
        if (body != fromApi.body) return true
        if (speaker != fromApi.speaker) return true
        if (twitter != fromApi.twitter) return true
        if (level != fromApi.level) return true
        return false
    }

    companion object {
        fun getAll(): Flowable<List<Session>> {
            return Single.concat(fromDb(), fromApi())
        }

        private fun fromApi(): Single<List<Session>> {
            return WebApi().getContent().map {
                updateTable(it)
            }
        }


        private fun updateTable(fromApiList: List<Session>): List<Session> {
            val orma = OrmaHolder.ORMA
            orma.transactionSync {
                fromApiList.map { fromApi ->
                    val fromDb: Session? = fromDbEqId(orma, fromApi.id)
                    if (fromDb != null) {
                        fromApi.favorite = fromDb.favorite
                        if (fromDb.requireUpdate(fromApi)) {
                            orma.updateSession().idEq(fromApi.id)
                                    .location(fromApi.location)
                                    .timeTable(fromApi.timeTable)
                                    .timeTableSort(fromApi.timeTableSort)
                                    .title(fromApi.title)
                                    .body(fromApi.body)
                                    .speaker(fromApi.speaker)
                                    .twitter(fromApi.twitter)
                                    .level(fromApi.level)
                                    .execute()

                        }
                    } else {
                        orma.insertIntoSession(fromApi)

                    }
                    fromApi

                }

            }
            return fromApiList
        }

        private fun fromDbEqId(orma: OrmaDatabase, id: Long): Session? = orma.selectFromSession().idEq(id).firstOrNull()

        fun fromDb(): Single<MutableList<Session>> {
            return OrmaHolder.ORMA.selectFromSession()
                    .orderByIdAsc()
                    .executeAsObservable()
                    .subscribeOn(Schedulers.io())
                    .toList()
        }
    }

}





