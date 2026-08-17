package com.etnstudio.user.domain.usecase

import java.security.MessageDigest
import java.time.Instant
import javax.inject.Inject

class LockVerifier @Inject constructor() {
    fun verify(code: String, hash: String?, expiry: Instant?): Boolean {
        if (hash == null) return true
        if (expiry != null && expiry.isBefore(Instant.now())) return false
        val inputHash = sha256(code)
        return inputHash.equals(hash, ignoreCase = true)
    }

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
