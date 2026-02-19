package com.tattai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tattai.data.ChatService
import com.tattai.domain.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val composer: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(private val chat: ChatService) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state

    fun setComposer(text: String) { _state.value = _state.value.copy(composer = text, error = null) }

    fun send() {
        val text = _state.value.composer.trim()
        if (text.isBlank() || _state.value.isLoading) return

        val next = _state.value.messages + ChatMessage(ChatMessage.Role.USER, text)
        _state.value = _state.value.copy(messages = next, composer = "", isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val reply = chat.ask(next)
                _state.value = _state.value.copy(
                    messages = _state.value.messages + ChatMessage(ChatMessage.Role.ASSISTANT, reply),
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
            }
        }
    }
}
