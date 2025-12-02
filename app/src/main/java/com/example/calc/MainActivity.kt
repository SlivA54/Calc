package com.example.calc

import android.os.Bundle
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
fun TipCalculatorApp(modifier: Modifier = Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var numDishes by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(0f) }

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
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Калькулятор чаевых",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = numDishes,
            onValueChange = { numDishes = it },
            label = { Text("Количество блюд") },
            modifier = Modifier.fillMaxWidth()
        )



    }
}


