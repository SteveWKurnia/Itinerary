package com.wkitinerary.ui.composables.models

data class Month(
    val monthNumber: Int,
) {
    fun getDaysOfMonth(year: Int): List<Int> {
        return if (monthNumber % 2 != 0) {
            populateDays(31)
        } else if (monthNumber == 2) {
            if (year % 4  == 0) populateDays(29) else populateDays(28)
        } else populateDays(30)
    }

    private fun populateDays(upperBound: Int): List<Int> {
        val list = mutableListOf<Int>()
        (1..upperBound).forEach {
            list.add(it)
        }
        return list
    }
}