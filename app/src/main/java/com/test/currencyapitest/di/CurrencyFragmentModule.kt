package com.test.currencyapitest.di

import android.app.Application
import android.view.View
import androidx.databinding.DataBindingUtil
import com.test.currencyapitest.R
import com.test.currencyapitest.adapter.CurrencyAdapter
import com.test.currencyapitest.interfaces.OnItemAmountUpdateListener
import com.test.currencyapitest.interfaces.OnItemAmountUpdateListenerImpl
import com.test.currencyapitest.interfaces.OnItemClickListener
import com.test.currencyapitest.interfaces.OnItemClickListenerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CurrencyFragmentModule {
    @Provides
    fun providesOnItemClickListener(): OnItemClickListener {
        return OnItemClickListenerImpl()
    }

    @Provides
    fun providesOnItemAmountUpdateListener(): OnItemAmountUpdateListener {
        return OnItemAmountUpdateListenerImpl()
    }

    @Provides
    fun providesCurrencyAdapter(onItemClickListener: OnItemClickListener,
                                onItemAmountUpdateListener: OnItemAmountUpdateListener)
            : CurrencyAdapter {
        return CurrencyAdapter(onItemClickListener, onItemAmountUpdateListener, null)
    }

//    @Provides
//    fun providesApplication()
//            : CurrencyAdapter {
//        return
//    }
}