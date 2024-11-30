package domain.services

import controllers.request.NotificationRequest
import controllers.request.OtpConfirmationRequest
import domain.clients.SmsNotificationsClient
import domain.dto.Notification
import domain.dto.StorableConfirmationNotification
import domain.repositories.OtpCodeRepository
import exceptions.NotificationNotFoundException
import exceptions.OtpCodeException
import utils.ConfirmationUtils.generateCode


class SmsMessageService(
    private val otpCodeRepository: OtpCodeRepository,
    private val smsNotificationsClient: SmsNotificationsClient
) {
    suspend fun sendConfirmNotification(userId: Long, notificationRequest: NotificationRequest) {
        val otpCode = generateCode()

        smsNotificationsClient.sendNotification(
            Notification(
                receiverPhoneNumber = notificationRequest.receiverPhoneNumber,
                message = "Ваш код подтверждения $otpCode. Не говорите его никому."
            )
        )

        otpCodeRepository.save(
            userId,
            StorableConfirmationNotification(notificationRequest.receiverPhoneNumber, otpCode)
        )
    }

    fun verifyOtpCode(userId: Long, otpConfirmationRequest: OtpConfirmationRequest) {
        val notification = otpCodeRepository.get(userId) ?: throw NotificationNotFoundException("otpCode.unknown.key")

        if (notification.otpCode != otpConfirmationRequest.otpCode) throw OtpCodeException("otpCode.incorrect")
        else {
            otpCodeRepository.delete(userId)
        }
    }
}