package com.test.currencyapitest.interfaces

import android.util.Log
import com.test.currencyapitest.viewmodel.CurrencyViewModel
import javax.inject.Inject

class OnItemAmountUpdateListenerImpl : OnItemAmountUpdateListener {

    @set:Inject
    var currencyViewModel: CurrencyViewModel?= null

    override fun onAmountEntered(currencyCode: String?, amount: String) {
        currencyViewModel?.updateMutableLiveData(currencyCode, amount)
    }
}