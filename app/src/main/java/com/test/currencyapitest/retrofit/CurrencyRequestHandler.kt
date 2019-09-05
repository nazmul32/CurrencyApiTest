package com.test.currencyapitest.retrofit

import android.util.Log
import com.test.currencyapitest.interfaces.OnResponseReceivedListener
import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.model.CurrencyDataStore
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyRequestHandler @Inject constructor(private var scheduler: Scheduler,
                                                 private var apiService: ApiService) {
    private var disposable: Disposable? = null
    private val tag = "RequestProcessor"
    private var onResponseReceivedListener: OnResponseReceivedListener?= null
    private var isAlreadyProcessed = false

    fun sendCurrencyDataRequest() {
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

    fun setOnResponseReceivedListener(onResponseReceivedListener: OnResponseReceivedListener) {
        this.onResponseReceivedListener = onResponseReceivedListener
    }
}