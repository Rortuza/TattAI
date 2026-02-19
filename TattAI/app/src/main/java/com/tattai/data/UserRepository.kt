package com.tattai.data

import com.tattai.domain.model.UserProfile
import com.tattai.util.Secrets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepository(private val firebase: FirebaseGateway?) {
    private val profileFlow = MutableStateFlow<UserProfile?>(null)
    fun observeProfile(): StateFlow<UserProfile?> = profileFlow

    fun saveProfile(profile: UserProfile) {
        profileFlow.value = profile
        if (Secrets.firebaseEnabled && firebase != null) firebase.saveUserProfile(profile)
    }
}
