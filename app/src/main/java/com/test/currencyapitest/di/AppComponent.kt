package com.test.currencyapitest.di

import android.app.Application
import com.test.currencyapitest.application.AndroidApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class, CurrencyFragmentModule::class,
    ActivityBuilder::class, ViewModelModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: AndroidApp)
}