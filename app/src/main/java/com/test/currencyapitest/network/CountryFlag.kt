package com.test.currencyapitest.network

import android.annotation.TargetApi
import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import com.test.currencyapitest.R
import java.util.*
import kotlin.collections.HashMap

class CountryFlag private constructor() {
    companion object {
        private val countryFlagMap = HashMap<String?, Int>()

        init {
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

        fun getFlagImageDrawable(currencyCode: String): Int? {
            return countryFlagMap[currencyCode]
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
}