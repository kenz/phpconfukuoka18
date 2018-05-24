package org.firespeed.phpconfukuoka18.api

import io.reactivex.Observable
import retrofit2.http.GET


interface HttpApi {
    @GET("/")
    fun getContent(): Observable<String>
}