package com.github.kisthy.avalveiculos.screens

import com.github.kisthy.avalveiculos.R

sealed class Screen(val route: String, val icon: Int, val title: Int) {
    data object Home : Screen("home", R.drawable.ic_aval, R.string.aval)
    data object History : Screen("historico", R.drawable.ic_history, R.string.history)
}
