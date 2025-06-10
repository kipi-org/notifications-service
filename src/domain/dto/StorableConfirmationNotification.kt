package domain.dto

import java.io.Serializable

data class StorableConfirmationNotification(
    val receiverPhoneNumber: String,
    val otpCode: String
): Serializable