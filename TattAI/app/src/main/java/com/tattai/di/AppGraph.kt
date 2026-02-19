package com.tattai.di

import com.tattai.data.ChatService
import com.tattai.data.MenuRepository
import com.tattai.data.UserRepository
import com.tattai.data.FirebaseGateway
import com.tattai.data.remote.OpenAiClient
import com.tattai.ui.ChatViewModel
import com.tattai.util.Secrets
import okhttp3.OkHttpClient

object AppGraph {
    private val http by lazy { OkHttpClient() }
    private val openAi by lazy { OpenAiClient(http = http) }

    val menuRepo: MenuRepository by lazy { MenuRepository() }

    private val firebaseGateway: FirebaseGateway? by lazy {
        if (!Secrets.firebaseEnabled) return@lazy null
        try {
            val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            com.tattai.data.firebase.FirebaseGatewayImpl(auth, db)
        } catch (_: Throwable) { null }
    }

    val userRepo: UserRepository by lazy { UserRepository(firebaseGateway) }

    private val chatService by lazy { ChatService(openAi, menuRepo, userRepo) }

    fun chatViewModel(): ChatViewModel = ChatViewModel(chatService)
}
