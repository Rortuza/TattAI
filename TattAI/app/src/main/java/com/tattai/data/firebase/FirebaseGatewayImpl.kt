package com.tattai.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tattai.data.FirebaseGateway
import com.tattai.domain.model.UserProfile

class FirebaseGatewayImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : FirebaseGateway {

    override fun saveUserProfile(profile: UserProfile) {
        val user = auth.currentUser ?: return
        val doc = db.collection("users").document(user.uid)
        val payload = mapOf(
            "fitnessGoal" to profile.fitnessGoal.name,
            "dietaryRestriction" to profile.dietaryRestriction.name,
            "macroTargets" to mapOf(
                "calories" to profile.macroTargets.calories,
                "proteinGrams" to profile.macroTargets.proteinGrams
            )
        )
        doc.set(payload)
    }
}
