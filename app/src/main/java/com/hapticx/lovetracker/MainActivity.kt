package com.hapticx.lovetracker

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hapticx.lovelog.R
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

        sharedPreferences = getSharedPreferences("com.hapticx.lovetracker", MODE_PRIVATE)
        coupleDate = Utils.getDate(sharedPreferences)

        var startDestination =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                "mainScreen"
            else
                "splashScreen"

        if (coupleDate == null && startDestination == "mainScreen")
            startDestination = "firstStepScreen"

        setContent {
            val ctx = LocalContext.current
            var permissionGranted by remember { mutableStateOf(
                ContextCompat.checkSelfPermission(
                    ctx, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                permissionGranted = it
            }

            if (!permissionGranted)
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        TextButton(onClick = {
                            if (!permissionGranted)
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }) {
                            Text(text = ctx.getString(R.string.allow))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = ctx.getString(R.string.decline))
                        }
                    },
                    title = {
                        Text(text = ctx.getString(R.string.please_allow))
                    },
                    text = {
                        Text(text = ctx.getString(R.string.allow_read_storage))
                    }
                )

            LoveLogTheme {
                val navController = rememberNavController()
                navController.visibleEntries

                LoveLogTheme {
                    NavHost(navController = navController, startDestination = startDestination) {
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
                        composable("mainScreen") {

                        }
                    }
                }
            }
        }
    }
}
