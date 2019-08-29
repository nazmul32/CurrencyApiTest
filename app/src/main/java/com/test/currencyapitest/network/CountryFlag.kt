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
            countryFlagMap["au"] = R.drawable.ic_flag_au
            countryFlagMap["bg"] = R.drawable.ic_flag_bg
            countryFlagMap["br"] = R.drawable.ic_flag_br
            countryFlagMap["ca"] = R.drawable.ic_flag_ca
            countryFlagMap["ch"] = R.drawable.ic_flag_ch
            countryFlagMap["cn"] = R.drawable.ic_flag_cn
            countryFlagMap["cz"] = R.drawable.ic_flag_cz
            countryFlagMap["dk"] = R.drawable.ic_flag_dk
            countryFlagMap["gb"] = R.drawable.ic_flag_gb
            countryFlagMap["hk"] = R.drawable.ic_flag_hk
            countryFlagMap["hr"] = R.drawable.ic_flag_hr
            countryFlagMap["hu"] = R.drawable.ic_flag_hu
            countryFlagMap["id"] = R.drawable.ic_flag_id
            countryFlagMap["il"] = R.drawable.ic_flag_il
            countryFlagMap["in"] = R.drawable.ic_flag_in
            countryFlagMap["is"] = R.drawable.ic_flag_is
            countryFlagMap["jp"] = R.drawable.ic_flag_jp
            countryFlagMap["kr"] = R.drawable.ic_flag_kr
            countryFlagMap["mx"] = R.drawable.ic_flag_mx
            countryFlagMap["my"] = R.drawable.ic_flag_my
            countryFlagMap["no"] = R.drawable.ic_flag_no
            countryFlagMap["nz"] = R.drawable.ic_flag_nz
            countryFlagMap["ph"] = R.drawable.ic_flag_ph
            countryFlagMap["pl"] = R.drawable.ic_flag_pl
            countryFlagMap["ro"] = R.drawable.ic_flag_ro
            countryFlagMap["ru"] = R.drawable.ic_flag_ru
            countryFlagMap["se"] = R.drawable.ic_flag_se
            countryFlagMap["sg"] = R.drawable.ic_flag_sg
            countryFlagMap["th"] = R.drawable.ic_flag_th
            countryFlagMap["tr"] = R.drawable.ic_flag_tr
            countryFlagMap["us"] = R.drawable.ic_flag_us
            countryFlagMap["za"] = R.drawable.ic_flag_za
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