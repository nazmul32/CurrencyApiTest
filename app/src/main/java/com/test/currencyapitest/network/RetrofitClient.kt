package com.test.currencyapitest.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private var retrofit: Retrofit? = null
        fun getRetrofitClient(): Retrofit {
            val retrofitInstance = retrofit
            if (retrofitInstance != null) {
                return retrofitInstance
            }

            val temp = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.createAsync()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
            retrofit = temp
            return temp
        }
    }
}
