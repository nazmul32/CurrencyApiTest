package com.test.currencyapitest.interfaces

import com.test.currencyapitest.model.ApiResponse
import retrofit2.Response

interface OnResponseReceivedListener {
    fun onResponseReceived(response: Response<ApiResponse>)
}