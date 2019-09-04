package com.test.currencyapitest.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.currencyapitest.model.CurrencyDatum
import com.test.currencyapitest.model.CurrencyDataStore
import java.util.*
import kotlin.collections.ArrayList
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import javax.inject.Inject


class CurrencyViewModel @Inject constructor() : ViewModel() {
    private val currenciesLiveData: MutableLiveData<ArrayList<CurrencyDatum>> = MutableLiveData()
    private val currenciesLiveDataUpdateRates: MutableLiveData<ArrayList<CurrencyDatum>> =
        MutableLiveData()

    private val currenciesData: ArrayList<CurrencyDatum> = ArrayList()
    val visibilityProgressBar: MutableLiveData<Int> = MutableLiveData()
    val visibilityRecyclerView: MutableLiveData<Int> = MutableLiveData()

    fun getCurrenciesLiveData(): MutableLiveData<ArrayList<CurrencyDatum>> {
        return currenciesLiveData
    }

    fun getCurrenciesLiveDataForUpdateRates(): MutableLiveData<ArrayList<CurrencyDatum>> {
        return currenciesLiveDataUpdateRates
    }

    fun updateMutableLiveData(countryCodes: List<String>?) {
        countryCodes?.forEach { s -> process(s) }
        currenciesLiveData.postValue(currenciesData)
    }

    fun updateMutableLiveData(
        currencyCode: String?,
        amount: String
    ) {
        if (amount.isEmpty()) {
            updateCurrencyRateForEmptyValue()
        } else {
            updateCurrencyRateForNonEmptyValue(currencyCode, amount)
        }
    }

    private fun updateCurrencyRateForNonEmptyValue(
        currencyCode: String?,
        amount: String
    ) {
        val currencyRate = CurrencyDataStore.instance.getCurrencyRateMap()[currencyCode]
        if (currencyRate != null) {
            val currency = amount.toDouble() / currencyRate
            currenciesData.forEach { t: CurrencyDatum? ->
                if (t != null) {
                    val convertedRate =
                        currency * CurrencyDataStore.instance.getCurrencyRateMap()[t.currencyCode]!!
                    t.currencyAmount = "%.2f".format(convertedRate)
                }
            }
            currenciesLiveDataUpdateRates.postValue(currenciesData)
        }
    }

    private fun updateCurrencyRateForEmptyValue() {
        currenciesData.forEach { t: CurrencyDatum? ->
            if (t != null) {
                t.currencyAmount = ""
            }
        }
        currenciesLiveDataUpdateRates.postValue(currenciesData)
    }

    fun hideProgressBar() {
        visibilityProgressBar.postValue(View.GONE)
        visibilityRecyclerView.postValue(View.VISIBLE)
    }

    fun showProgressBar() {
        visibilityProgressBar.postValue(View.VISIBLE)
        visibilityRecyclerView.postValue(View.GONE)
    }

    private fun process(s: String) {
        val currencyDatum = CurrencyDatum()
        currencyDatum.currencyAmount = ""
        currencyDatum.currencyCode = s

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            currencyDatum.currencyName = Currency.getInstance(s).displayName
        } else {
            currencyDatum.currencyName = CurrencyDataStore.instance.getCurrencyName(s)
        }

        currencyDatum.flagImageId = CurrencyDataStore.instance.getFlagImageDrawable(s)
        currenciesData.add(currencyDatum)
    }
}