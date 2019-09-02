package com.test.currencyapitest.model

import com.test.currencyapitest.R
import kotlin.collections.HashMap

class CurrencyDataStore {
    private val countryFlagMap = HashMap<String?, Int>()
    private val currencyNameMap = HashMap<String?, String>()
    private lateinit var currencyRateMap: Map<String, Double>

    init {
        initFlagsData()
        initCurrencyNameData()
    }

    companion object {
        val instance = CurrencyDataStore()
    }

    fun getFlagImageDrawable(currencyCode: String): Int? {
        return countryFlagMap[currencyCode]
    }

    fun getCurrencyName(currencyCode: String): String? {
        if (currencyCode == "") {
            return ""
        }
        return currencyNameMap[currencyCode]
    }

    fun getCurrencyRateMap(): Map<String, Double> {
        return currencyRateMap
    }

    fun setCurrencyRateMap(currencyRateMap: Map<String, Double>) {
        synchronized(this) {
            this.currencyRateMap = currencyRateMap
        }
    }

    private fun initFlagsData() {
        countryFlagMap["AUD"] = R.drawable.ic_flag_au
        countryFlagMap["BGN"] = R.drawable.ic_flag_bg
        countryFlagMap["BRL"] = R.drawable.ic_flag_br
        countryFlagMap["CAD"] = R.drawable.ic_flag_ca
        countryFlagMap["CHF"] = R.drawable.ic_flag_ch
        countryFlagMap["CNY"] = R.drawable.ic_flag_cn
        countryFlagMap["CZK"] = R.drawable.ic_flag_cz
        countryFlagMap["DKK"] = R.drawable.ic_flag_dk
        countryFlagMap["GBP"] = R.drawable.ic_flag_gb
        countryFlagMap["HKD"] = R.drawable.ic_flag_hk
        countryFlagMap["HRK"] = R.drawable.ic_flag_hr
        countryFlagMap["HUF"] = R.drawable.ic_flag_hu
        countryFlagMap["IDR"] = R.drawable.ic_flag_id
        countryFlagMap["ILS"] = R.drawable.ic_flag_il
        countryFlagMap["INR"] = R.drawable.ic_flag_in
        countryFlagMap["ISK"] = R.drawable.ic_flag_is
        countryFlagMap["JPY"] = R.drawable.ic_flag_jp
        countryFlagMap["KRW"] = R.drawable.ic_flag_kr
        countryFlagMap["MXN"] = R.drawable.ic_flag_mx
        countryFlagMap["MYR"] = R.drawable.ic_flag_my
        countryFlagMap["NOK"] = R.drawable.ic_flag_no
        countryFlagMap["NZD"] = R.drawable.ic_flag_nz
        countryFlagMap["PHP"] = R.drawable.ic_flag_ph
        countryFlagMap["PLN"] = R.drawable.ic_flag_pl
        countryFlagMap["RON"] = R.drawable.ic_flag_ro
        countryFlagMap["RUB"] = R.drawable.ic_flag_ru
        countryFlagMap["SEK"] = R.drawable.ic_flag_se
        countryFlagMap["SGD"] = R.drawable.ic_flag_sg
        countryFlagMap["THB"] = R.drawable.ic_flag_th
        countryFlagMap["TRY"] = R.drawable.ic_flag_tr
        countryFlagMap["USD"] = R.drawable.ic_flag_us
        countryFlagMap["ZAR"] = R.drawable.ic_flag_za
    }

    private fun initCurrencyNameData() {
        currencyNameMap["AUD"] = "Australian Dollar"
        currencyNameMap["BGN"] = "Bulgarian Lev"
        currencyNameMap["BRL"] = "Brazilian Real"
        currencyNameMap["CAD"] = "Canadian Dollar"
        currencyNameMap["CHF"] = "Swiss Franc"
        currencyNameMap["CNY"] = "Chinese Yuan"
        currencyNameMap["CZK"] = "Czech Republic Koruna"
        currencyNameMap["DKK"] = "Danish Krone"
        currencyNameMap["GBP"] = "British Pound"
        currencyNameMap["HKD"] = "Hong Kong Dollar"
        currencyNameMap["HRK"] = "Croatian Kuna"
        currencyNameMap["HUF"] = "Hungarian Forint"
        currencyNameMap["IDR"] = "Indonesian Rupiah"
        currencyNameMap["ILS"] = "Israeli New Shekel"
        currencyNameMap["INR"] = "Indian Rupee"
        currencyNameMap["ISK"] = "Icelandic Króna"
        currencyNameMap["JPY"] = "Japanese Yen"
        currencyNameMap["KRW"] = "South Korean Won"
        currencyNameMap["MXN"] = "Mexican Peso"
        currencyNameMap["MYR"] = "Malaysian Ringgit"
        currencyNameMap["NOK"] = "Norwegian Krone"
        currencyNameMap["NZD"] = "New Zealand Dollar"
        currencyNameMap["PHP"] = "Philippine Peso"
        currencyNameMap["PLN"] = "Polish Zloty"
        currencyNameMap["RON"] = "Romanian Leu"
        currencyNameMap["RUB"] = "Russian Ruble"
        currencyNameMap["SEK"] = "Swedish Krona"
        currencyNameMap["SGD"] = "Singapore Dollar"
        currencyNameMap["THB"] = "Thai Baht"
        currencyNameMap["TRY"] = "Turkish Lira"
        currencyNameMap["USD"] = "US Dollar"
        currencyNameMap["ZAR"] = "South African Rand"
    }
}