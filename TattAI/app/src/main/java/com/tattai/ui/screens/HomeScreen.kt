package com.tattai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onOpenChat: () -> Unit, onOpenMenu: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("TattAI") }) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Personalized menu help; without the awkward small talk.", style = MaterialTheme.typography.titleMedium)
            ElevatedButton(onClick = onOpenChat, modifier = Modifier.fillMaxWidth()) { Text("Ask TattAI") }
            OutlinedButton(onClick = onOpenMenu, modifier = Modifier.fillMaxWidth()) { Text("Browse Menu") }
            Spacer(Modifier.weight(1f))
            Text("Try: “High-protein breakfast under 600 calories.”", style = MaterialTheme.typography.bodySmall)
        }
    }
}
