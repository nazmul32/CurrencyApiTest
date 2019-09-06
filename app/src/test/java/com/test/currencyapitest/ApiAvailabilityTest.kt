package com.test.currencyapitest

import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedReader
import java.net.URL

class ApiAvailabilityTest {
    @Test
    @Throws(Exception::class)
    fun testAvailability(){
        val connection = URL("https://revolut.duckdns.org/latest?base=EUR").openConnection()
        val response = connection.getInputStream()
        val buffer = response.bufferedReader().use(BufferedReader::readText)
        assertTrue(buffer.isNotEmpty())
    }
}
