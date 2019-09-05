package com.test.currencyapitest.retrofit

import com.test.currencyapitest.model.ApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    fun getCurrencyData(@Query("base") base: String) : Single<Response<ApiResponse>>
}
