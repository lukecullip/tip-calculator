package com.example.tip_calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tip_calculator.ui.theme.Tip_CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tip_CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculator()
                }
            }
        }
    }
}

@Composable
fun TipCalculator() {
    var checkAmount by remember { mutableStateOf("") }
    var selectedTipAmount by remember { mutableStateOf<Int?>(null) }
    var tipAmount by remember { mutableStateOf<Double?>(null) }
    var totalAmount by remember { mutableStateOf<Double?>(null) }
    var errorOnNull by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tip Calculator", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(25.dp))

        Text("Enter your check amount:")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = checkAmount,
                onValueChange = { checkAmount = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text("Select your tip amount:")

        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(10, 20, 30).forEach { percent ->
                Button(
                    onClick = {
                        selectedTipAmount = percent
                        errorOnNull = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                    if (selectedTipAmount == percent) Color(0xFF6200EE) else Color.LightGray,
                    contentColor = Color.Black,
                ),
                    modifier = Modifier.padding(horizontal = 7.dp)
                ) {
                    Text("$percent%")
                }
            }
        }

        Button(
            onClick = {
                val amount = checkAmount.toDoubleOrNull()
                if (amount == null || selectedTipAmount == null) {
                    errorOnNull = true
                    tipAmount = null
                    totalAmount = null
                } else {
                    errorOnNull = false
                    tipAmount = (amount * selectedTipAmount!!) / 100
                    totalAmount = amount + tipAmount!!
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text("Calculate", color = Color.White)
        }

        when {
            errorOnNull -> Text("Error", color = Color.Black)
            tipAmount != null && totalAmount != null -> {
                Text("Tip: $${"%.2f".format(tipAmount)}")
                Text("Total: $${"%.2f".format(totalAmount)}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tip_CalculatorTheme {
        TipCalculator()
    }
}