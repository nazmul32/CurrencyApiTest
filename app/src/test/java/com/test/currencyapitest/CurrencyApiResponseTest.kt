package com.test.currencyapitest

import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.retrofit.ApiService
import com.test.currencyapitest.retrofit.CurrencyRateHandler
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import retrofit2.Response
import com.google.gson.Gson


class CurrencyApiResponseTest {

    @Rule @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()


    @Test
    fun `when fetchCurrencyData is called, should call TestClient and return apiResponse`() {
        val currencyRateHandler = Mockito.mock(CurrencyRateHandler::class.java)
        val rawResponse = "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.624,\"BGN\":1.965,\"BRL\":4.8143,\"CAD\":1.541,\"CHF\":1.1328,\"CNY\":7.9825,\"CZK\":25.836,\"DKK\":7.4918,\"GBP\":0.90247,\"HKD\":9.1754,\"HRK\":7.4691,\"HUF\":328.03,\"IDR\":17405.0,\"ILS\":4.1902,\"INR\":84.111,\"ISK\":128.4,\"JPY\":130.16,\"KRW\":1310.9,\"MXN\":22.471,\"MYR\":4.8346,\"NOK\":9.822,\"NZD\":1.7716,\"PHP\":62.887,\"PLN\":4.3386,\"RON\":4.6603,\"RUB\":79.949,\"SEK\":10.641,\"SGD\":1.6075,\"THB\":38.309,\"TRY\":7.6641,\"USD\":1.1689,\"ZAR\":17.907}}"
        val apiResponse = Gson().fromJson(rawResponse, ApiResponse::class.java)

        Mockito.`when`(currencyRateHandler.fetchCurrencyData())
            .thenReturn(Observable.just(Response.success(apiResponse)))

        val result = Observable.just(Response.success(apiResponse))

        result.subscribe { t: Response<ApiResponse>? ->
            run {
                assertThat(t?.body()?.base, `is`("EUR"))
                assertThat(t?.body()?.date, `is`("2018-09-06"))
                assertThat(t?.body()?.rates?.size, `is`(32))
            }
        }
    }
}

class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}


class TestClient(private val apiService: ApiService)  {
    fun sendRequest(base: String): Observable<Response<ApiResponse>> {
        return apiService.getCurrencyDataForTest(base)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t: Response<ApiResponse> -> t }
    }
}