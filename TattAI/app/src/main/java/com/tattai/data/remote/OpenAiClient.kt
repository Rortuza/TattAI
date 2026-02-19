package com.tattai.data.remote

import com.tattai.domain.model.ChatMessage
import com.tattai.util.Secrets
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class OpenAiClient(
    private val http: OkHttpClient = OkHttpClient(),
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    @Throws(IOException::class)
    fun getAssistantReply(systemPrompt: String, messages: List<ChatMessage>): String {
        val apiKey = Secrets.openAiApiKey ?: throw IOException("Missing OPENAI_API_KEY (see README)")
        val model = Secrets.openAiModel

        val payload = ChatRequest(
            model = model,
            input = buildList {
                add(InputItem(role = "system", content = systemPrompt))
                messages.forEach { m ->
                    add(InputItem(role = if (m.role == ChatMessage.Role.USER) "user" else "assistant", content = m.content))
                }
            }
        )

        val body = json.encodeToString(ChatRequest.serializer(), payload).toRequestBody(mediaType)

        val req = Request.Builder()
            .url("https://api.openai.com/v1/responses")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        http.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) {
                val err = resp.body?.string() ?: ""
                throw IOException("OpenAI error: HTTP ${resp.code}; $err")
            }
            val raw = resp.body?.string() ?: throw IOException("Empty OpenAI response")
            val parsed = json.decodeFromString(ChatResponse.serializer(), raw)
            return parsed.outputText() ?: "No response text returned."
        }
    }

    @Serializable data class ChatRequest(val model: String, val input: List<InputItem>)
    @Serializable data class InputItem(val role: String, val content: String)

    @Serializable data class ChatResponse(val output: List<OutputItem> = emptyList()) {
        fun outputText(): String? {
            for (o in output) for (c in o.content) if (c.type == "output_text" && c.text != null) return c.text
            return null
        }
    }
    @Serializable data class OutputItem(val content: List<OutputContent> = emptyList())
    @Serializable data class OutputContent(val type: String, val text: String? = null)
}
