package com.tattai.domain.model

data class MenuItem(
    val id: String,
    val name: String,
    val category: String,
    val calories: Int,
    val proteinGrams: Int,
    val tags: List<String> = emptyList()
)

enum class FitnessGoal(val displayName: String) {
    LeanBulk("Lean bulk"),
    Cut("Cut"),
    Maintenance("Maintenance");

    companion object { val entries: List<FitnessGoal> = values().toList() }
}

enum class DietaryRestriction(val displayName: String) {
    None("None"),
    Vegetarian("Vegetarian"),
    Vegan("Vegan"),
    GlutenFree("Gluten-free"),
    DairyFree("Dairy-free"),
    NutAllergy("Nut allergy");

    companion object { val entries: List<DietaryRestriction> = values().toList() }
}

data class MacroTargets(val calories: Int, val proteinGrams: Int)

data class UserProfile(
    val fitnessGoal: FitnessGoal,
    val dietaryRestriction: DietaryRestriction,
    val macroTargets: MacroTargets
)

data class ChatMessage(val role: Role, val content: String) {
    enum class Role { USER, ASSISTANT }
}
