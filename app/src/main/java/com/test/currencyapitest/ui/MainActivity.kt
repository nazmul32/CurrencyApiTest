package com.test.currencyapitest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.currencyapitest.R
import com.test.currencyapitest.dagger.DaggerAppComponent
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyFragment: CurrencyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.builder().build().inject(this)
        supportActionBar?.elevation = 2f
        supportActionBar?.title = getString(R.string.rates)
        addCurrencyFragment()
    }

    private fun addCurrencyFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, currencyFragment)
        transaction.commit()
    }
}
