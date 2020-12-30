package com.example.quizzy.utils

import com.example.quizzy.R

object IconPicker {
    val icons = arrayOf(
            R.drawable.cinema,
            R.drawable.history,
            R.drawable.ic_food,
            R.drawable.ic_tech,
            R.drawable.ic_math,
            R.drawable.ic_soprts,
            R.drawable.ic_map,
            R.drawable.newspaper
    )
    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}
