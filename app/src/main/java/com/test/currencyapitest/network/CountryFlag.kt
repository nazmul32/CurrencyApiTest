package com.test.currencyapitest.network

import android.annotation.TargetApi
import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import java.util.*
import kotlin.collections.HashMap

class CountryFlag private constructor() {
    companion object {
        private val countryFlagMap = HashMap<String?, String>()

        fun getFlagImageUrl(currencyCode: String): String? {
            return countryFlagMap[currencyCode]
        }

        fun updateFlagImageUrl(currencyCode: String) {
            if (!countryFlagMap.containsKey(currencyCode)) {
                val url = "https://www.countryflags.io/${currencyCode.toLowerCase().substring(0, 2)}/flat/64.png"
                countryFlagMap[currencyCode] = url
                Log.v("countryFlagMap", currencyCode.toLowerCase().substring(0, 2))
            }
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