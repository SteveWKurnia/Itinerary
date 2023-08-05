package com.wkitinerary.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CustomDispatcher {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}

class CustomDispatcherImpl: CustomDispatcher {
    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main
}
