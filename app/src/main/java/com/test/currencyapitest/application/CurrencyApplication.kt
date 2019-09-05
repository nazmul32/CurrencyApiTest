package com.test.currencyapitest.application

import android.app.Application
import com.test.currencyapitest.dagger.AppComponent
import com.test.currencyapitest.dagger.DaggerAppComponent

class CurrencyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}