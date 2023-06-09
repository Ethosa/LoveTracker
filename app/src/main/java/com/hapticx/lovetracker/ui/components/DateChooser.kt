package com.hapticx.lovetracker.ui.components


import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hapticx.lovelog.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateChooserField(
    modifier: Modifier = Modifier,
    initDate: String? = null,
    formattedDate: String = "##.##.####",
    onEdit: (date: String) -> Unit = {},
    onPick: (date: String) -> Unit = {}
) {
    // Get current date
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val ctx = LocalContext.current

    val years = year.toString().padStart(4, '0')
    val months = (month+1).toString().padStart(2, '0')
    val days = day.toString().padStart(2, '0')

    // Write current date as string
    var date by remember { mutableStateOf(TextFieldValue(
        initDate ?: "$days.$months.$years"
    )) }
    var index by remember { mutableStateOf(0) }
    var isError by remember { mutableStateOf(false) }

    val datePickerDialog = DatePickerDialog(
        ctx,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            val ys = y.toString().padStart(4, '0')
            val ms = (m+1).toString().padStart(2, '0')
            val ds = d.toString().padStart(2, '0')
            date = TextFieldValue("$ds.$ms.$ys")
            onPick(date.text)
        },
        year, month, day
    )

    OutlinedTextField(
        date,
        onValueChange = {
            if (it.text.length > formattedDate.length)
                return@OutlinedTextField
            index = if (it.selection.start > 0) it.selection.start-1 else 0
            val isLonger = it.text.length > date.text.length
            date = when {
                formattedDate[index] == '#' -> it
                isLonger -> TextFieldValue(
                    it.text + formattedDate[index],
                    selection = TextRange(it.selection.start + 1, it.selection.end + 1)
                )
                else -> it
            }
            if (index < formattedDate.length-1 && formattedDate[index+1] != '#' && isLonger) {
                date = TextFieldValue(
                    date.text + formattedDate[index+1],
                    selection = TextRange(date.selection.start + 1, date.selection.end + 1)
                )
            }
            onEdit(date.text)
        },
        isError = isError,
        placeholder = {
            Text(
                "DD.MM.YYYY",
                color=MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        label = {
            Text(
                ctx.getString(R.string.date),
                color=MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            autoCorrect = false,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (date.text.length < formattedDate.length)
                isError = true
            else
                onPick(date.text)
        }),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Outlined.CalendarMonth, "calendar")
            }
        },
        modifier = modifier
    )
}