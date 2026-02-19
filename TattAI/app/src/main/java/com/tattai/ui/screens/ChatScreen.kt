package com.tattai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tattai.di.AppGraph
import com.tattai.domain.model.ChatMessage

@Composable
fun ChatScreen() {
    val vm = remember { AppGraph.chatViewModel() }
    val state by vm.state.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Ask TattAI") }) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.messages) { MessageBubble(it) }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.composer,
                    onValueChange = vm::setComposer,
                    label = { Text("Ask for recommendations") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(onClick = { vm.send() }, enabled = !state.isLoading && state.composer.isNotBlank()) {
                    Text(if (state.isLoading) "..." else "Send")
                }
            }

            if (state.error != null) {
                Text(state.error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun MessageBubble(msg: ChatMessage) {
    val isUser = msg.role == ChatMessage.Role.USER
    val container = if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start) {
        Surface(color = container, shape = MaterialTheme.shapes.medium) {
            Column(Modifier.padding(10.dp)) { Text(msg.content, color = textColor) }
        }
    }
}
