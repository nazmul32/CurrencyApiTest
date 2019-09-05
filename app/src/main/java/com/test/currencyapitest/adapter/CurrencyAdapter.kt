package com.test.currencyapitest.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.ViewDataBinding
import com.test.currencyapitest.databinding.ListTypeCurrencyEmptyBinding
import com.test.currencyapitest.databinding.ListTypeCurrencyNormalBinding
import com.test.currencyapitest.interfaces.OnItemAmountUpdateListener
import com.test.currencyapitest.interfaces.OnItemClickListener
import com.test.currencyapitest.model.CurrencyDatum
import com.test.currencyapitest.util.Constants
import kotlinx.android.synthetic.main.list_type_currency_normal.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList



class CurrencyAdapter @Inject constructor(
    private val onItemClickListener: OnItemClickListener,
    private val onItemAmountUpdateListener: OnItemAmountUpdateListener,
    private var currencyDatumList: ArrayList<CurrencyDatum>?
) : RecyclerView.Adapter<CurrencyAdapter.BaseViewHolder<*>>() {

    private var askingFocusExternally = false
    private var previousAmount:String? = null
    private var prevLen = 0
    private var currLen = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == Constants.NORMAL_VIEW_TYPE) {
            val applicationBinding = ListTypeCurrencyNormalBinding.inflate(inflater, parent, false)
            val viewHolder = NormalViewHolder(applicationBinding)
            applicationBinding.root.setOnClickListener {
                updateAdapterOnClick(
                    applicationBinding,
                    viewHolder.adapterPosition
                )
            }
            applicationBinding.etCurrency.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val str = s?.trim().toString()
                    currLen = str.length
                    if (currLen < prevLen) {
                        if (str != previousAmount && viewHolder.adapterPosition == 0) {
                            previousAmount = str
                            onItemAmountUpdateListener.onAmountEntered(
                                currencyDatumList?.get(viewHolder.adapterPosition)?.currencyCode,
                                str
                            )
                        }
                    } else {
                        if (str.isNotEmpty() && str != previousAmount && viewHolder.adapterPosition == 0) {
                            previousAmount = str
                            onItemAmountUpdateListener.onAmountEntered(
                                currencyDatumList?.get(viewHolder.adapterPosition)?.currencyCode,
                                str
                            )
                        }
                    }


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (viewHolder.adapterPosition == 0) {
                        prevLen = s?.length ?: 0
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    return
                }
            })
            applicationBinding.etCurrency.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus && !askingFocusExternally) {
                        updateAdapterOnClick(applicationBinding, viewHolder.adapterPosition)
                    }
                }
            viewHolder
        } else {
            val applicationBinding = ListTypeCurrencyEmptyBinding.inflate(inflater, parent, false)
            EmptyViewHolder(applicationBinding)
        }
    }

    override fun getItemCount(): Int = currencyDatumList?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder.itemViewType == Constants.NORMAL_VIEW_TYPE) {
            holder.bind(position)
        } else {
            holder.bind()
        }
    }

    fun updateAdapter(currencyDatumList: ArrayList<CurrencyDatum>) {
        this.currencyDatumList = currencyDatumList
        notifyDataSetChanged()
    }

    fun updateAdapterForRates(currencyDatumList: ArrayList<CurrencyDatum>) {
        if (itemCount > 1) {
            this.currencyDatumList = currencyDatumList
            notifyItemRangeChanged(1, itemCount - 1)
        }
    }

    private fun updateAdapterOnClick(applicationBinding: ListTypeCurrencyNormalBinding, position: Int) {
        askingFocusExternally = true
        applicationBinding.etCurrency.requestFocus()
        Collections.swap(currencyDatumList, 0, position)
        notifyItemMoved(position, 0)
        onItemClickListener.onItemClick()
        askingFocusExternally = false
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount == 0) {
            Constants.EMPTY_VIEW_TYPE
        } else {
            Constants.NORMAL_VIEW_TYPE
        }
    }

    abstract class BaseViewHolder<T>(applicationBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(applicationBinding.root) {
        abstract fun bind(position: Int)
        abstract fun bind()
    }

    inner class NormalViewHolder(private var applicationBinding: ListTypeCurrencyNormalBinding) :
        BaseViewHolder<Int>(applicationBinding) {
        override fun bind(position: Int) {
            applicationBinding.currencyDatum = currencyDatumList?.get(position)
            applicationBinding.currencyDatum?.setImageSrc(itemView.iv_flag)
            applicationBinding.currencyDatum?.setCurrencyAmount(itemView.et_currency, applicationBinding.currencyDatum?.currencyAmount)
        }

        override fun bind() {
            return
        }
    }


    inner class EmptyViewHolder(private var applicationBinding: ListTypeCurrencyEmptyBinding) :
        BaseViewHolder<Int>(applicationBinding) {
        override fun bind() {
            return
        }

        override fun bind(position: Int) {
            return
        }
    }

    override fun getItemId(position: Int): Long {
        return (currencyDatumList?.get(position)?.currencyCode?.hashCode() ?: 0).toLong()
    }
}

