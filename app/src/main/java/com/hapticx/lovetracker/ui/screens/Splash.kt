package com.hapticx.lovetracker.ui.screens


import android.content.SharedPreferences
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hapticx.lovelog.R
import com.hapticx.lovetracker.Utils
import com.hapticx.lovetracker.ui.theme.SplashScreenBackground
import kotlinx.coroutines.delay


@Stable
@Composable
fun SplashScreen(navController: NavController, preferences: SharedPreferences) {
    // Animations
    val scale = remember { Animatable(1f) }
    val offsetY = remember { Animatable(128f) }
    val alpha = remember { Animatable(0f) }

    val date by remember { mutableStateOf(Utils.getDate(preferences)) }

    // Scale
    LaunchedEffect(key1 = true) {
        scale.animateTo(2f, tween(500, easing = EaseInExpo, delayMillis = 300))
        delay(800)
        scale.animateTo(1f, tween(500, easing = EaseOutExpo))
    }

    // Alpha
    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f, tween(500, easing = EaseInExpo, delayMillis = 300))
        delay(800)
        alpha.animateTo(0f, tween(500, easing = EaseOutExpo))
    }

    // Offset
    LaunchedEffect(key1 = true) {
        offsetY.animateTo(0f, tween(500, easing = EaseInExpo, delayMillis = 300))
        delay(800)
        offsetY.animateTo(-128f, tween(500, easing = EaseOutExpo))
        delay(500)
        navController.popBackStack()
        when (date) {
            null -> navController.navigate("firstStepScreen")
            else -> navController.navigate("mainScreen")
        }
    }

    Surface(
        color = SplashScreenBackground
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .offset(0.dp, offsetY.value.dp)
                .scale(scale.value)
                .alpha(alpha.value),
            contentAlignment = Alignment.Center
        ) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                "Logo"
            )
        }
    }
}
