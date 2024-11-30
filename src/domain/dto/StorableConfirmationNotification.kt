package domain.dto

data class StorableConfirmationNotification(
    val receiverPhoneNumber: String,
    val otpCode: String
)