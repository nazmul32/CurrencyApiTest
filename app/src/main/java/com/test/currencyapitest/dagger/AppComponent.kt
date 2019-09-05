package com.test.currencyapitest.dagger

import com.test.currencyapitest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CurrencyFragmentModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}