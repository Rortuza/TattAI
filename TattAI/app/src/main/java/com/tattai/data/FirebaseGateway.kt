package com.tattai.data

import com.tattai.domain.model.UserProfile

interface FirebaseGateway { fun saveUserProfile(profile: UserProfile) }
