@file:OptIn(ExperimentalComposeUiApi::class)

package com.hapticx.lovetracker.ui.screens

import android.content.SharedPreferences
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hapticx.lovelog.R
import com.hapticx.lovetracker.ui.components.DateChooserField
import com.hapticx.lovetracker.ui.theme.FirstStep1
import com.hapticx.lovetracker.ui.theme.FirstStep2
import com.hapticx.lovetracker.ui.theme.FirstStep3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstStepScreen(navController: NavHostController, sharedPreferences: SharedPreferences) {
    var coupleDate by remember { mutableStateOf("") }
    var state by remember { mutableStateOf(0) }
    var backgroundColor by remember { mutableStateOf(FirstStep1) }

    var heName by remember { mutableStateOf(TextFieldValue("He")) }
    var sheName by remember { mutableStateOf(TextFieldValue("She")) }

    val ctx = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    Surface(color = backgroundColor) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (state) {
                0 -> Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = ctx.getString(R.string.when_you_starting_dating),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    DateChooserField(
                        onPick = {
                            coupleDate = it
                            keyboard?.hide()
                        }
                    )
                }
                1 -> Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = ctx.getString(R.string.choose_avatars),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            Modifier.width(160.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                ImageBitmap.imageResource(id = R.drawable.she),
                                "She",
                                Modifier.size(128.dp)
                            )
                            OutlinedTextField(
                                value = sheName,
                                onValueChange = {
                                    sheName = it
                                }
                            )
                        }
                        Spacer(Modifier.width(32.dp))
                        Column(
                            Modifier.width(160.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                ImageBitmap.imageResource(id = R.drawable.he),
                                "He",
                                Modifier.size(128.dp)
                            )
                            OutlinedTextField(
                                value = heName,
                                onValueChange = {
                                    heName = it
                                }
                            )
                        }
                    }
                }
                else -> Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = ctx.getString(R.string.choose_avatars),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            Modifier.width(160.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                ImageBitmap.imageResource(id = R.drawable.she),
                                "She",
                                Modifier.size(128.dp)
                            )
                            OutlinedTextField(
                                value = sheName,
                                onValueChange = {
                                    sheName = it
                                }
                            )
                        }
                        Spacer(Modifier.width(32.dp))
                        Column(
                            Modifier.width(160.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                ImageBitmap.imageResource(id = R.drawable.he),
                                "He",
                                Modifier.size(128.dp)
                            )
                            OutlinedTextField(
                                value = heName,
                                onValueChange = {
                                    heName = it
                                }
                            )
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .padding(0.dp, 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    contentPadding = PaddingValues(48.dp, 0.dp),
                    onClick = {
                        state++
                        backgroundColor = when(state) {
                            1 -> FirstStep2
                            2 -> FirstStep3
                            else -> FirstStep1
                        }
                        if (state > 2) {
                            navController.navigate("mainScreen")
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = ctx.getString(R.string.cont),
                        fontSize = 14.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    for (i in 0..2) {
                        Surface(
                            Modifier.size(8.dp),
                            color =
                                if (i == state)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.tertiary,
                            shape = CircleShape
                        ) {}
                    }
                }
            }
        }
    }
}