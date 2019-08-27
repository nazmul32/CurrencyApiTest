package com.test.currencyapitest
import ApiResponse
import android.annotation.TargetApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONObject
import android.os.Build
import android.icu.text.NumberFormat
import com.test.currencyapitest.network.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import com.test.currencyapitest.network.RetrofitClient
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private var myCompositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendCurrencyDataRequest()
    }

    private fun sendCurrencyDataRequest() {
        val retrofitClient = RetrofitClient.getRetrofitClient()
        val apiService = retrofitClient.create(APIService::class.java)
        myCompositeDisposable?.add(apiService.getCurrencyData("EUR")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onNext, this::onError, this::onComplete, this::onSubscribe))
    }

    private fun onNext(response: ApiResponse) {
        Log.v("handleResponse", response.base)
    }

    private fun onError(e: Throwable) {
        Log.v("handleResponse", e.message)
    }

    private fun onSubscribe(d: Disposable) {
        Log.v("handleResponse", "" + d.isDisposed)
    }

    private fun onComplete() {
        Log.v("handleResponse", "onComplete")
    }


    private fun printCountryIso() {
        val response = "{\"base\":\"EUR\",\"date\":\"2018-09-06\"," +
                "\"rates\":{\"AUD\":1.6087,\"BGN\":1.9465,\"BRL\":4.7689," +
                "\"CAD\":1.5265,\"CHF\":1.1221,\"CNY\":7.9072,\"CZK\":25.592," +
                "\"DKK\":7.4211,\"GBP\":0.89395,\"HKD\":9.0888,\"HRK\":7.3986," +
                "\"HUF\":324.93,\"IDR\":17241.0,\"ILS\":4.1507,\"INR\":83.318," +
                "\"ISK\":127.19,\"JPY\":128.93,\"KRW\":1298.5,\"MXN\":22.259," +
                "\"MYR\":4.789,\"NOK\":9.7293,\"NZD\":1.7549,\"PHP\":62.293," +
                "\"PLN\":4.2977,\"RON\":4.6164,\"RUB\":79.195,\"SEK\":10.54," +
                "\"SGD\":1.5924,\"THB\":37.948,\"TRY\":7.5918,\"USD\":1.1578,\"ZAR\":17.738}}"

        val jsonObject = JSONObject(response).getJSONObject("rates")
        jsonObject.keys().forEach { s -> printIso(s) }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun printIso(currencyCode: String) {
        Log.v("printCountryIso", "https://restcountries.eu/data/" +  getLocale(currencyCode)?.isO3Country?.toLowerCase() + ".svg")
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getLocale(strCode: String): Locale? {
        for (locale in NumberFormat.getAvailableLocales()) {
            val code = NumberFormat.getCurrencyInstance(locale).currency?.currencyCode
            if (strCode == code) {
                return locale
            }
        }
        return null
    }
}
