package com.test.currencyapitest.model

import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.currencyapitest.R

class CurrencyDatum : BaseObservable() {
    var currencyCode: String? = null
    var currencyName: String? = null
    var flagImageId: Int? = null
    var currencyAmount: String? = null

    @BindingAdapter("android:src")
    fun setImageSrc(view: ImageView) {
        Glide.with(view.context).load(flagImageId).error(R.drawable.ic_flag_none).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC).into(view)
    }

    fun setCurrencyAmount(editText: EditText, amount: String?) {
       editText.setText(amount)
    }
}
