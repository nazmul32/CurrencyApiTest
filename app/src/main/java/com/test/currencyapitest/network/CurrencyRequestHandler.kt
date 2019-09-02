package com.test.currencyapitest.network

import android.util.Log
import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.model.CurrencyDataStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CurrencyRequestHandler(onResponseReceivedListener: OnResponseReceivedListener) {
    private var disposable: Disposable? = null
    private val tag = "RequestProcessor"
    private var onResponseReceivedListener: OnResponseReceivedListener?= onResponseReceivedListener
    private var isAlreadyProcessed = false

    fun sendCurrencyDataRequest() {
        val retrofitClient = RetrofitClient.getRetrofitClient()
        val apiService = retrofitClient.create(APIService::class.java)
        val scheduler = Schedulers.from(Executors.newSingleThreadExecutor())
        disposable =
            Observable.interval(0, 1, TimeUnit.SECONDS, scheduler)
                .flatMapSingle { apiService.getCurrencyData("EUR") }
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .distinct()
                .subscribe({ result -> onSuccess(result) }, { error -> onError(error) })
    }

    private fun onSuccess(response: Response<ApiResponse>) {
        if (!isAlreadyProcessed) {
            onResponseReceivedListener?.onResponseReceived(response)
            isAlreadyProcessed = true
        }
        CurrencyDataStore.instance.setCurrencyRateMap(response.body()?.rates!!)
    }

    private fun onError(e: Throwable?) {
        Log.v(tag, "onError: {${e?.message}}")
    }

    fun dispose() {
        disposable?.dispose()
    }

    interface OnResponseReceivedListener {
        fun onResponseReceived(response: Response<ApiResponse>)
    }
}