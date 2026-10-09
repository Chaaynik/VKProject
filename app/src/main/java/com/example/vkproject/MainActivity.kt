package com.example.vkproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkproject.ui.theme.VKProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VKProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ExplicitIntentSection()
        HorizontalDivider()
        ImplicitIntentSection()
        HorizontalDivider()
        SystemIntentSection()
    }
}

// 1. Явный intent
@Composable
fun ExplicitIntentSection() {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("1. Явный intent")

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val text = inputText.trim()

                if (text.isEmpty()) {
                    Toast.makeText(context, "Введите текст", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(context, SecondActivity::class.java)
                    intent.putExtra("text", text)
                    context.startActivity(intent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Открыть вторую Activity")
        }
    }
}

// 2. Неявный intent
@Composable
fun ImplicitIntentSection() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("2. Неявный intent")

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Номер телефона") },
            placeholder = { Text("+7xxxxxxxxxx") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val phone = phoneNumber.trim()

                if (phone.isEmpty()) {
                    Toast.makeText(context, "Введите номер телефона", Toast.LENGTH_SHORT).show()
                } else {

                    val pattern = Regex("^\\+7[0-9]{10}$")

                    if (!pattern.matches(phone)) {
                        Toast.makeText(context, "Некорректный номер телефона", Toast.LENGTH_SHORT).show()
                    } else {
                        var digitCount = 0
                        for (symbol in phone) {
                            if (symbol.isDigit()) {
                                digitCount = digitCount + 1
                            }
                        }

                        if (digitCount < 10) {
                            Toast.makeText(context, "В номере должно быть минимум 10 цифр", Toast.LENGTH_SHORT).show()
                        } else {

                            val intent = Intent(Intent.ACTION_DIAL)
                            val uri = Uri.parse("tel:" + phone)
                            intent.data = uri
                            context.startActivity(intent)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Позвонить другу")
        }
    }
}

// 3. Системный intent
@Composable
fun SystemIntentSection() {
    val context = LocalContext.current
    var shareText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("3. Системный intent")

        OutlinedTextField(
            value = shareText,
            onValueChange = { shareText = it },
            label = { Text("Текст для отправки") },
            placeholder = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val text = shareText.trim()

                if (text.isEmpty()) {
                    Toast.makeText(context, "Введите текст", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, text)

                    val chooser = Intent.createChooser(intent, "Поделиться через")
                    context.startActivity(chooser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться текстом")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    VKProjectTheme {
        MainScreen()
    }
}