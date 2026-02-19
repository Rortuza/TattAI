package com.tattai.data

import com.tattai.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuRepository {
    private val menu = MutableStateFlow(sampleMenu())
    fun observeMenu(): Flow<List<MenuItem>> = menu.asStateFlow()

    private fun sampleMenu(): List<MenuItem> = listOf(
        MenuItem("1", "Greek Yogurt Parfait", "Breakfast", 420, 25, listOf("High-protein")),
        MenuItem("2", "Egg White Sandwich", "Breakfast", 480, 32, listOf("Lean")),
        MenuItem("3", "Chicken & Quinoa Bowl", "Lunch", 610, 45, listOf("High-protein")),
        MenuItem("4", "Avocado Toast", "Breakfast", 520, 14, listOf("Vegetarian")),
        MenuItem("5", "Turkey Salad", "Lunch", 390, 35, listOf("Low-calorie"))
    )
}
