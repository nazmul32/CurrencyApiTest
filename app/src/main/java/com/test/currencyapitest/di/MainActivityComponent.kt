package com.test.currencyapitest.di

import com.test.currencyapitest.ui.MainActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component (modules = [CurrencyFragmentModule::class, ViewModelModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}