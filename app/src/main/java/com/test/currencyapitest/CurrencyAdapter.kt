package com.test.currencyapitest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_type_normal.view.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.currencyapitest.network.CentralDataModel
import com.bumptech.glide.request.RequestOptions
import android.os.Build
import android.util.Log
import com.test.currencyapitest.network.Constants
import java.util.*


class CurrencyAdapter(private val glide: RequestManager, private val listener: OnItemClickListener) : RecyclerView.Adapter<CurrencyAdapter.BaseViewHolder<*>>() {
    private var keys: MutableList<String>? = null
    private var values: MutableList<Double>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == Constants.NORMAL_VIEW_TYPE) {
            val view = inflater.inflate(R.layout.list_type_normal, parent, false)
            val viewHolder = NormalViewHolder(view)
            view.setOnClickListener{ listener.onItemClick(viewHolder.adapterPosition, keys, values) }
            viewHolder
        } else {
            val view = inflater.inflate(R.layout.list_type_empty, parent, false)
            EmptyViewHolder(view)
        }
    }

    override fun getItemCount(): Int = keys?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder.itemViewType == Constants.NORMAL_VIEW_TYPE) {
            holder.bind(position)
        } else {
            holder.bind()
        }
    }

    fun updateAdapter(keys: MutableList<String>?, values: MutableList<Double>?) {
        this.keys = keys
        this.values = values
        notifyDataSetChanged()
    }

    fun showFlagImage(resId: Int?, view: ImageView) {
        if (resId == null) {
            glide.load(R.drawable.ic_flag_none).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
        } else {
            glide.load(resId).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (keys?.size == 0) {
            Constants.EMPTY_VIEW_TYPE
        } else {
            Constants.NORMAL_VIEW_TYPE
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, keys: MutableList<String>?, values: MutableList<Double>?)
    }

    inner class NormalViewHolder(view: View) : BaseViewHolder<Int>(view) {

        override fun bind() {
            return
        }

        override fun bind(position: Int) {
            with(itemView) {
                tv_currency_code.text = keys?.get(position) ?: ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    tv_currency_name.text =  Currency.getInstance(keys?.get(position) ?: "").displayName
                } else {
                    tv_currency_name.text =  CentralDataModel.getCurrencyName(keys?.get(position) ?: "")
                }

                Log.v("bind-view", "" + position + " " + tv_currency_code.text)
                showFlagImage(CentralDataModel.getFlagImageDrawable(keys?.get(position) ?: ""),
                    iv_flag)
            }
        }
    }

    inner class EmptyViewHolder(view: View) : BaseViewHolder<Int>(view) {
        override fun bind() {
            return
        }

        override fun bind(position: Int) {
            return
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position: Int)
        abstract fun bind()
    }

    fun updateAdapterOnClick(keys: MutableList<String>?, values: MutableList<Double>?, fromPosition: Int, toPosition: Int) {
        this.keys = keys
        this.values = values
        notifyItemMoved(fromPosition, toPosition)
    }
}