package com.test.currencyapitest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_type_normal.view.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.currencyapitest.network.CountryFlag
import com.bumptech.glide.request.RequestOptions
import android.graphics.drawable.PictureDrawable
import android.R.transition
import android.util.Log
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


class CurrencyAdapter(val glide: RequestManager) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var key: List<String>? = null
    private var value: List<Double>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_type_normal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = key?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            with(itemView) {
                tv_name.text = key?.get(position) ?: ""
                tv_currency.text =  value?.get(position)?.toString() ?: ""

                showFlagImage(CountryFlag.getFlagImageDrawable(key?.get(position) ?: ""),
                    iv_flag)
            }
        }
    }

    fun updateAdapter(key: List<String>?, value: List<Double>?) {
        this.key = key
        this.value = value
        notifyDataSetChanged()
    }

    fun showFlagImage(resId: Int?, view: ImageView) {
        if (resId == null) {
            glide.load(R.drawable.ic_flag_none).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
        } else {
            glide.load(resId).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
        }
    }
}