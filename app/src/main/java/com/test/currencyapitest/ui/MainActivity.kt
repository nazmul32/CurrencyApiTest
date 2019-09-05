package com.test.currencyapitest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.test.currencyapitest.R
import com.test.currencyapitest.dagger.DaggerAppComponent
import com.test.currencyapitest.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import javax.inject.Inject
import java.net.InetAddress


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyFragment: CurrencyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {showErrorOnInternetUnavailable() }).start()

        DaggerAppComponent.builder().build().inject(this)
        supportActionBar?.elevation = 2f
        supportActionBar?.title = getString(R.string.rates)
        addCurrencyFragment()

    }

    private fun showErrorOnInternetUnavailable() {
        var message = ""
        message = if (NetworkHelper.isNetworkConnectionAvailable(context = this)) {
            try {
                val address = InetAddress.getByName("8.8.8.8")
                val reachable = address.isReachable(5000)
                if (!reachable) {
                    getString(R.string.internet_connection_might_not_be_available)
                }
                ""
            } catch (e: Exception) {
                "Error in connectivity checking. Please try later."
            }
        } else {
            getString(R.string.internet_connection_not_available)
        }
        if (message.isNotEmpty()) {
            runOnUiThread { Snackbar.make(container, message, Snackbar.LENGTH_LONG).show() }
        }
    }

    private fun addCurrencyFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, currencyFragment)
        transaction.commit()
    }
}
