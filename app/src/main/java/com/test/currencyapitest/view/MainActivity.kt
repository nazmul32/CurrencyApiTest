package com.test.currencyapitest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.currencyapitest.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 2f
        supportActionBar?.title = getString(R.string.rates)
        addCurrencyFragment()
    }

    private fun addCurrencyFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, CurrencyFragment.newInstance())
        transaction.commit()
    }
}
