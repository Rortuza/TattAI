\
package com.tattai.data

import com.tattai.data.remote.OpenAiClient
import com.tattai.domain.model.ChatMessage
import com.tattai.domain.model.MenuItem
import com.tattai.domain.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ChatService(
    private val openAi: OpenAiClient,
    private val menuRepo: MenuRepository,
    private val userRepo: UserRepository
) {
    suspend fun ask(history: List<ChatMessage>): String = withContext(Dispatchers.IO) {
        val profile = userRepo.observeProfile().value
        val menu = menuRepo.observeMenu().first()
        val systemPrompt = buildSystemPrompt(profile, menu)
        openAi.getAssistantReply(systemPrompt, history)
    }

    private fun buildSystemPrompt(profile: UserProfile?, menu: List<MenuItem>): String {
        val profileText = if (profile == null) {
            "User profile: unknown. Ask 1–2 quick clarifying questions if needed."
        } else {
            "User profile: goal=${profile.fitnessGoal.displayName}, restriction=${profile.dietaryRestriction.displayName}, targets=${profile.macroTargets.calories} cal and ${profile.macroTargets.proteinGrams}g protein."
        }

        val menuText = menu.joinToString(separator = "\n") {
            "- ${it.name} (${it.category}): ${it.calories} cal, ${it.proteinGrams}g protein. tags=${it.tags.joinToString(",")}"
        }

        return """
You are TattAI, a helpful nutrition assistant for a cafe menu.
Your job: recommend 2–4 menu items that best match the user's goals and restrictions.
Always include: (1) a short rationale, (2) estimated macros from the item data, (3) an optional swap or customization tip.

$profileText

Menu items (use only these unless the user asks for general advice):
$menuText

If the user asks for something that violates a restriction, explain and suggest safe alternatives.
Keep the tone friendly and concise.
""".trim()
    }
}
