package com.tattai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tattai.di.AppGraph
import com.tattai.domain.model.DietaryRestriction
import com.tattai.domain.model.FitnessGoal
import com.tattai.domain.model.MacroTargets
import com.tattai.domain.model.UserProfile

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    var goal by remember { mutableStateOf(FitnessGoal.LeanBulk) }
    var restriction by remember { mutableStateOf(DietaryRestriction.None) }
    var protein by remember { mutableStateOf("180") }
    var calories by remember { mutableStateOf("2200") }

    Scaffold(topBar = { TopAppBar(title = { Text("TattAI Setup") }) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Tell TattAI what you're optimizing for.", style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FitnessGoal.entries.forEach { g ->
                    FilterChip(selected = goal == g, onClick = { goal = g }, label = { Text(g.displayName) })
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DietaryRestriction.entries.forEach { r ->
                    FilterChip(selected = restriction == r, onClick = { restriction = r }, label = { Text(r.displayName) })
                }
            }

            OutlinedTextField(
                value = protein,
                onValueChange = { protein = it.filter(Char::isDigit).take(4) },
                label = { Text("Daily protein target (g)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it.filter(Char::isDigit).take(5) },
                label = { Text("Daily calorie target") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            Button(onClick = {
                val profile = UserProfile(
                    fitnessGoal = goal,
                    dietaryRestriction = restriction,
                    macroTargets = MacroTargets(
                        calories = calories.toIntOrNull() ?: 0,
                        proteinGrams = protein.toIntOrNull() ?: 0
                    )
                )
                AppGraph.userRepo.saveProfile(profile)
                onDone()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Continue")
            }

            Text("You can change this later; TattAI doesn't judge, it just calculates.", style = MaterialTheme.typography.bodySmall)
        }
    }
}
