package com.example.calc

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calc.ui.theme.CalcTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorApp() {
    var billAmount by remember { mutableStateOf("") }
    var numDishes by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15) }

    val bill = billAmount.toFloatOrNull() ?: 0f
    val dishes = numDishes.toIntOrNull() ?: 0
    val discountPercent = when {
        dishes in 1..2 -> 3
        dishes in 3..5 -> 5
        dishes in 6..10 -> 7
        dishes > 10 -> 10
        else -> 0
    }
    val finalAmount = ((bill * (1 - discountPercent / 100f)) * (1 + tipPercent / 100f)).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Калькулятор чаевых", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Сумма счёта") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = numDishes,
            onValueChange = { numDishes = it },
            label = { Text("Количество блюд") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Чаевые (%)", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Slider(
            value = tipPercent.toFloat(),
            onValueChange = { tipPercent = it.roundToInt() },
            valueRange = 0f..25f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("$tipPercent%", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Скидка: $discountPercent%", fontSize = 18.sp)

                RadioGroup(
                    discountPercent,
                    listOf(3, 5, 7, 10),
                    onCheckedChange = { }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Итоговая сумма: $finalAmount ₽",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


