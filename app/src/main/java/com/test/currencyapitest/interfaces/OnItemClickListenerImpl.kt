package com.test.currencyapitest.interfaces

import android.widget.Toast
import com.test.currencyapitest.databinding.FragmentCurrencyBinding
import kotlinx.android.synthetic.main.fragment_currency.view.*
import javax.inject.Inject

class OnItemClickListenerImpl: OnItemClickListener {
    @set:Inject
    var binding: FragmentCurrencyBinding?= null

    override fun onItemClick() {
        binding?.root?.recycler_view?.scrollToPosition(0)
//        Toast.makeText(binding?.root?.context, "hi", Toast.LENGTH_SHORT).show()
    }
}