package com.tattai

import com.tattai.data.remote.OpenAiClient
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class OpenAiPayloadTest {
    @Test fun `request serializes`() {
        val json = Json { ignoreUnknownKeys = true }
        val req = OpenAiClient.ChatRequest(
            model = "gpt-4o-mini",
            input = listOf(OpenAiClient.InputItem(role = "system", content = "hi"))
        )
        val raw = json.encodeToString(OpenAiClient.ChatRequest.serializer(), req)
        assertTrue(raw.contains("gpt-4o-mini"))
        assertTrue(raw.contains("system"))
    }
}
