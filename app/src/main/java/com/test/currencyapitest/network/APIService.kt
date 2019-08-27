package com.test.currencyapitest.network

import ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("latest")
    fun getCurrencyData(@Query("base") base: String) : Observable<ApiResponse>
}
