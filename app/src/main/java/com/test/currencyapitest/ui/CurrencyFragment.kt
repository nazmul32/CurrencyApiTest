package com.test.currencyapitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.test.currencyapitest.R
import com.test.currencyapitest.adapter.CurrencyAdapter
import com.test.currencyapitest.databinding.FragmentCurrencyBinding
import com.test.currencyapitest.interfaces.OnItemAmountUpdateListener
import com.test.currencyapitest.interfaces.OnItemClickListener
import com.test.currencyapitest.interfaces.OnResponseReceivedListener
import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.retrofit.CurrencyRateHandler
import com.test.currencyapitest.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.fragment_currency.view.*
import retrofit2.Response
import javax.inject.Inject

class CurrencyFragment @Inject constructor(): Fragment() {
    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var binding: FragmentCurrencyBinding
    private lateinit var currencyAdapter: CurrencyAdapter

    @Inject
    lateinit var currencyRateHandler: CurrencyRateHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        currencyViewModel.showProgressBar()
        currencyRateHandler.setOnResponseReceivedListener(onResponseReceivedListener)
        initRecyclerView()
        subscribeLiveDataObservers()
        return binding.root
    }

    private fun subscribeLiveDataObservers() {
        ViewModelProviders.of(this)[CurrencyViewModel::class.java].getCurrenciesLiveData().observe(this, androidx.lifecycle.Observer { t -> currencyAdapter.updateAdapter(t) })
        ViewModelProviders.of(this)[CurrencyViewModel::class.java].getCurrenciesLiveDataForUpdateRates().observe(this, androidx.lifecycle.Observer { t -> currencyAdapter.updateAdapterForRates(t) })
        ViewModelProviders.of(this)[CurrencyViewModel::class.java].visibilityProgressBar.observe(this, androidx.lifecycle.Observer { t -> progress_bar_container.visibility = t })
        ViewModelProviders.of(this)[CurrencyViewModel::class.java].visibilityRecyclerView.observe(this, androidx.lifecycle.Observer { t -> recycler_view.visibility = t })
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.root.recycler_view.layoutManager = layoutManager
        binding.root.recycler_view.setHasFixedSize(true)
        currencyAdapter = CurrencyAdapter(onItemClickListener, onItemAmountUpdateListener, null)
        binding.root.recycler_view.adapter = currencyAdapter
        binding.root.recycler_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    override fun onStart() {
        super.onStart()
        currencyRateHandler.sendCurrencyDataRequest()
    }

    override fun onStop() {
        super.onStop()
        currencyRateHandler.dispose()
    }

    private val onResponseReceivedListener = object : OnResponseReceivedListener {
        override fun onResponseReceived(response: Response<ApiResponse>) {
            currencyViewModel.hideProgressBar()
            if (response.isSuccessful) {
                currencyViewModel.updateMutableLiveData(response.body()?.rates?.keys?.toList())
            }
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick() {
            binding.root.recycler_view?.scrollToPosition(0)
        }
    }

    private val onItemAmountUpdateListener = object : OnItemAmountUpdateListener {
        override fun onAmountEntered(currencyCode: String?, amount: String) {
            currencyViewModel.updateMutableLiveData(currencyCode, amount)
        }
    }

}

