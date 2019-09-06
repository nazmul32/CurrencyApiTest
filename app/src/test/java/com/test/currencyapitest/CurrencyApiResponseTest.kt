package com.test.currencyapitest

import android.graphics.Movie
import com.google.gson.Gson
import com.test.currencyapitest.dagger.AppModule
import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.retrofit.ApiService
import com.test.currencyapitest.retrofit.CurrencyRequestHandler
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CurrencyApiResponseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    internal lateinit var underTest: ApiServiceTest

    @InjectMocks
    private val client = Client()

    @Before
    fun setUp() {
        underTest = ApiServiceTest()
    }

    @Test
    fun `when currency rates are requested, should call api and return response`() {
        val rawResponse = "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.624,\"BGN\":1.965,\"BRL\":4.8143,\"CAD\":1.541,\"CHF\":1.1328,\"CNY\":7.9825,\"CZK\":25.836,\"DKK\":7.4918,\"GBP\":0.90247,\"HKD\":9.1754,\"HRK\":7.4691,\"HUF\":328.03,\"IDR\":17405.0,\"ILS\":4.1902,\"INR\":84.111,\"ISK\":128.4,\"JPY\":130.16,\"KRW\":1310.9,\"MXN\":22.471,\"MYR\":4.8346,\"NOK\":9.822,\"NZD\":1.7716,\"PHP\":62.887,\"PLN\":4.3386,\"RON\":4.6603,\"RUB\":79.949,\"SEK\":10.641,\"SGD\":1.6075,\"THB\":38.309,\"TRY\":7.6641,\"USD\":1.1689,\"ZAR\":17.907}}"
        val apiResponse = Gson().fromJson(rawResponse, ApiResponse::class.java)

        Mockito.`when`(client.sendRequest("EUR")).thenReturn(Single.just(Response.success(apiResponse)))

        val result = underTest.requestCurrencyData("EUR")

        val testObserver = TestObserver<Single<Response<ApiResponse>>>()
        result.subscribe()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val response = result.blockingGet()
        assertThat(response.body()?.base, `is`("EUR"))
        assertThat(response.body()?.date, `is`("2018-09-06"))
        assertThat(response.body()?.rates?.size, `is`(32))
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

    class ApiServiceTest {
        fun requestCurrencyData(base: String): Single<Response<ApiResponse>> {
            val appModule = AppModule()
            return appModule.providesApiService(appModule.provideGson(),
                appModule.provideOkHttpClient()).getCurrencyData(base)
                .subscribeOn(appModule.provideScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Response.success(ApiResponse(it.body()?.base.toString(), it.body()?.date.toString(),
                        it.body()?.rates!!
                    ))
                }
        }
    }
    class Client {
        fun sendRequest(base: String): Single<Response<ApiResponse>> {
            val appModule = AppModule()
            return appModule.providesApiService(appModule.provideGson(),
                appModule.provideOkHttpClient()).getCurrencyData(base)
                .subscribeOn(appModule.provideScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Response.success(ApiResponse(it.body()?.base.toString(), it.body()?.date.toString(),
                        it.body()?.rates!!
                    ))
                }
        }
    }
}
