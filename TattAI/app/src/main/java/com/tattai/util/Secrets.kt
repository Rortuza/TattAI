package com.tattai.util

import java.util.Properties

object Secrets {
    private val props: Properties by lazy {
        val p = Properties()
        try {
            val stream = Secrets::class.java.classLoader?.getResourceAsStream("secrets.properties")
            if (stream != null) stream.use { p.load(it) }
        } catch (_: Exception) {}
        p
    }

    val openAiApiKey: String? get() = props.getProperty("OPENAI_API_KEY")
    val openAiModel: String get() = props.getProperty("OPENAI_MODEL") ?: "gpt-4o-mini"
    val firebaseEnabled: Boolean get() = (props.getProperty("FIREBASE_ENABLED") ?: "false").toBoolean()
}
