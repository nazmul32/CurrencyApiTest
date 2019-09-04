package com.test.currencyapitest.di

import com.test.currencyapitest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(CurrencyFragmentModule::class))
    abstract fun bindMainActivity(): MainActivity
}