package com.hapticx.lovetracker

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hapticx.lovetracker.ui.screens.FirstStepScreen
import com.hapticx.lovetracker.ui.screens.SplashScreen
import com.hapticx.lovetracker.ui.theme.EnterAnimation
import com.hapticx.lovetracker.ui.theme.LoveLogTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var coupleDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var startDestination =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                "mainScreen"
            } else {
                "splashScreen"
            }

        sharedPreferences = getSharedPreferences("com.avocat.lovelog", MODE_PRIVATE)
        coupleDate = Utils.getDate(sharedPreferences)

        if (coupleDate == null && startDestination == "mainScreen")
            startDestination = "firstStepScreen"

        setContent {
            LoveLogTheme {
                val navController = rememberNavController()
                navController.visibleEntries

                LoveLogTheme {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable("splashScreen") {
                            EnterAnimation {
                                SplashScreen(navController, sharedPreferences)
                            }
                        }
                        composable("firstStepScreen") {
                            EnterAnimation {
                                FirstStepScreen(navController, sharedPreferences)
                            }
                        }
                    }
                }
            }
        }
    }
}
