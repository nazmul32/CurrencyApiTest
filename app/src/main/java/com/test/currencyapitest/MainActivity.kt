package com.test.currencyapitest
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
import io.reactivex.disposables.CompositeDisposable
import java.util.Locale
import android.app.ActivityGroup
import io.reactivex.observers.DisposableObserver
import javax.xml.datatype.DatatypeConstants.SECONDS
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import ApiResponse
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.currencyapitest.network.CountryFlag
import io.reactivex.*
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers.from
import java.lang.System.err
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.Schedulers.from
import kotlinx.android.synthetic.main.activity_main.*
import org.reactivestreams.Subscriber
import retrofit2.Response
import java.io.InputStream
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private lateinit var disposable: Disposable
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        sendCurrencyDataRequest()
    }

    private fun sendCurrencyDataRequest() {
        val retrofitClient = RetrofitClient.getRetrofitClient()
        val apiService = retrofitClient.create(APIService::class.java)
        val scheduler = Schedulers.from(Executors.newSingleThreadExecutor())
        disposable =
            Observable.interval(0, 1, TimeUnit.SECONDS, scheduler)
                .flatMapSingle { t -> apiService.getCurrencyData("EUR") }
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .distinct()
                .subscribe({ result -> onSuccess(result) }, { error -> onError(error) })
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }

    private fun onSuccess(response: Response<ApiResponse>) {
        val keyList = response.body()?.rates?.keys?.toMutableList()
        val valueList = response.body()?.rates?.values?.toMutableList()
        saveCountryFlagImageUrl(keyList)
        currencyAdapter.updateAdapter(keyList, valueList)
    }

    private fun onError(e: Throwable?) {
        Log.v("handleResponse", "onError: {${e?.message}}")
    }

    private fun saveCountryFlagImageUrl(keyList: MutableList<String>?) {
        keyList?.forEach {
            CountryFlag.updateFlagImageUrl(it)
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        currencyAdapter = CurrencyAdapter(Glide.with(this))
        recycler_view.adapter = currencyAdapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
