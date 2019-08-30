package com.test.currencyapitest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.currencyapitest.network.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import com.test.currencyapitest.network.RetrofitClient
import ApiResponse
import android.graphics.drawable.PictureDrawable
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.currencyapitest.network.CountryFlag
import io.reactivex.*
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private var currencyAdapter: CurrencyAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
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

    override fun onStart() {
        super.onStart()
        sendCurrencyDataRequest()
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    private fun onSuccess(response: Response<ApiResponse>) {
        Log.v("handleResponse", "onSuccess")
        val keyList = response.body()?.rates?.keys?.toMutableList()
        keyList?.forEach { t: String? -> Log.v("keyList", t) }
        val valueList = response.body()?.rates?.values?.toMutableList()
        currencyAdapter?.updateAdapter(keyList, valueList)
//        runLayoutAnimation(recycler_view)
    }

    private fun onError(e: Throwable?) {
        Log.v("handleResponse", "onError: {${e?.message}}")
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        currencyAdapter = CurrencyAdapter(Glide.with(this))
        recycler_view.adapter = currencyAdapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        if (recyclerView.adapter != null) {
            val controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)

            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }
    }
}
