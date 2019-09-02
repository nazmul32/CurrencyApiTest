package com.test.currencyapitest.view

import android.os.Bundle
import android.util.Log
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
import com.test.currencyapitest.model.ApiResponse
import com.test.currencyapitest.network.CurrencyRequestHandler
import com.test.currencyapitest.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.fragment_currency.view.*
import retrofit2.Response

class CurrencyFragment : Fragment() {
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var currencyRequestHandler: CurrencyRequestHandler
    private lateinit var currencyViewModel: CurrencyViewModel

    private lateinit var binding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        currencyViewModel.showProgressBar()
        currencyRequestHandler = CurrencyRequestHandler(onResponseReceivedListener = listener)

        initRecyclerView()
        subscribeLiveDataObservers()
        return binding.root
    }

    private fun subscribeLiveDataObservers() {
        currencyViewModel.getCurrenciesLiveData().observe(this, androidx.lifecycle.Observer { t -> currencyAdapter.updateAdapter(t) })
        currencyViewModel.getCurrenciesLiveDataForUpdateRates().observe(this, androidx.lifecycle.Observer { t -> currencyAdapter.updateAdapterForRates(t) })
        currencyViewModel.visibilityProgressBar.observe(this, androidx.lifecycle.Observer { t -> progress_bar_container.visibility = t })
        currencyViewModel.visibilityRecyclerView.observe(this, androidx.lifecycle.Observer { t -> recycler_view.visibility = t })
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.root.recycler_view.layoutManager = layoutManager
        currencyAdapter = CurrencyAdapter(
            itemClickListener,
            null
        )
        binding.root.recycler_view.setHasFixedSize(true)
        binding.root.recycler_view.adapter = currencyAdapter
        binding.root.recycler_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private val itemClickListener = object : CurrencyAdapter.OnItemClickListener {
        override fun onItemClick() {
            binding.root.recycler_view.scrollToPosition(0)
        }

        override fun onAmountEntered(
            currencyCode: String?,
            amount: String
        ) {
            Log.v("onAmountEntered", "$currencyCode $amount")
            currencyViewModel.updateMutableLiveData(currencyCode, amount)
        }
    }

    private val listener = object : CurrencyRequestHandler.OnResponseReceivedListener {
        override fun onResponseReceived(response: Response<ApiResponse>) {
            currencyViewModel.hideProgressBar()
            if (response.isSuccessful) {
                currencyViewModel.updateMutableLiveData(response.body()?.rates?.keys?.toList())
            }
        }
    }
    
    companion object {
        @JvmStatic
        fun newInstance() = CurrencyFragment()
    }

    override fun onStart() {
        super.onStart()
        currencyRequestHandler.sendCurrencyDataRequest()
    }

    override fun onStop() {
        super.onStop()
        currencyRequestHandler.dispose()
    }
}

