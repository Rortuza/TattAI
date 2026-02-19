package com.tattai.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.tattai.ui.nav.TattAiNavGraph
import com.tattai.ui.theme.TattAiTheme

@Composable
fun AppRoot() {
    TattAiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            TattAiNavGraph(navController)
        }
    }
}
