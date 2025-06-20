package com.example.financemanager

import androidx.annotation.StringRes

enum class Screen(@StringRes val title: Int) {
    Balace(title = 0),
    Record(title = 1)
}