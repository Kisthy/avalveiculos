package com.github.kisthy.avalveiculos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.kisthy.avalveiculos.components.BottomNavigationBar
import com.github.kisthy.avalveiculos.screens.DetailsScreen
import com.github.kisthy.avalveiculos.screens.HistoryScreen
import com.github.kisthy.avalveiculos.screens.HomeScreen
import com.github.kisthy.avalveiculos.screens.HomeScreenViewModel
import com.github.kisthy.avalveiculos.screens.Screen
import com.github.kisthy.avalveiculos.ui.theme.AvalVeiculosTheme

class MainActivity : ComponentActivity() {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvalVeiculosTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(homeScreenViewModel)
                        }
                        composable(Screen.History.route) {
                            HistoryScreen(navController)
                        }
                        composable(
                            route = "details/{avaliacaoId}",
                            arguments = listOf(navArgument("avaliacaoId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val avaliacaoId = backStackEntry.arguments?.getLong("avaliacaoId")
                            DetailsScreen(avaliacaoId!!)
                        }
                    }
                }
            }
        }
    }
}

