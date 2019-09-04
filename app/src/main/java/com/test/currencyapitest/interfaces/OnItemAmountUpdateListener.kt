package com.test.currencyapitest.interfaces

interface OnItemAmountUpdateListener {
    fun onAmountEntered(
        currencyCode: String?,
        amount: String
    )
}