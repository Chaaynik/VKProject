package com.example.vkproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class StorefrontApp(
    val id: Int,
    val name: String,
    val icon: ImageVector
)

val storefrontPages = listOf(
    listOf(
        StorefrontApp(10, "VK Клипы", Icons.Default.PlayArrow),
        StorefrontApp(11, "VK Знакомства", Icons.Default.Favorite),
        StorefrontApp(12, "VK Документы", Icons.Default.Description)
    ),
    listOf(
        StorefrontApp(13, "VK Загрузки", Icons.Default.Download),
        StorefrontApp(14, "VK Видео Live", Icons.Default.LiveTv),
        StorefrontApp(15, "VK Музыка", Icons.Default.Headphones)
    ),
    listOf(
        StorefrontApp(16, "VK Мессенджер", Icons.Default.Message),
        StorefrontApp(17, "VK Видео", Icons.Default.OndemandVideo),
        StorefrontApp(18, "VK Клипы Live", Icons.Default.Videocam)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(navController: NavController, appId: Int?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Карточка приложения") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0077FF))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Приложение ID: ${appId ?: "неизвестно"}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Витрина", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            val pagerState = rememberPagerState(pageCount = { storefrontPages.size })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    storefrontPages[page].forEach { app ->
                        StorefrontMiniCard(
                            app = app,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("list") {
                                    popUpTo("list") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StorefrontMiniCard(
    app: StorefrontApp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = app.icon,
                contentDescription = app.name,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = app.name,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}