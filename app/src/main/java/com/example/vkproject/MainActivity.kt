package com.example.vkproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vkproject.ui.theme.VKProjectTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VKProjectTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        AppListScreen(navController = navController)
                    }
                    composable("detail/{appId}") { backStackEntry ->
                        val appId = backStackEntry.arguments?.getString("appId")?.toIntOrNull()
                        AppDetailScreen(navController = navController, appId = appId)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = "Иконка приложения",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "RuStore", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0077FF)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppCard(
                id = 1,
                icon = Icons.Default.Message,
                name = "VK Мессенджер",
                description = "Общение и звонки",
                category = "Общение",
                onClick = { navController.navigate("detail/1") }
            )
            HorizontalDivider()

            AppCard(
                id=2,
                icon = Icons.Default.PlayArrow,
                name = "VK Видео",
                description = "Фильмы, сериалы и ролики",
                category = "Видео",
                onClick = { navController.navigate("detail/2") }

            )
            HorizontalDivider()

            AppCard(
                id=3,
                icon = Icons.Default.Headphones,
                name = "VK Музыка",
                description = "Музыка и подкасты",
                category = "Музыка",
                onClick = { navController.navigate("detail/3") }

            )
            HorizontalDivider()

            AppCard(
                id=4,
                icon = Icons.Default.PeopleAlt,
                name = "VK Знакомства",
                description = "Дейтинг-сервис",
                category = "Знакомства",
                onClick = { navController.navigate("detail/4") }

            )
            HorizontalDivider()

            AppCard(
                id=5,
                icon = Icons.Default.Add,
                name = "Другое...",
                description = "Другие сервисы",
                category = "Разное",
                onClick = { navController.navigate("detail/5") }

            )
            HorizontalDivider()
        }
    }
}