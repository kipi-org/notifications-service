package domain.services

import Config
import controllers.request.NotificationRequest
import controllers.request.OtpConfirmationRequest
import domain.clients.SmsNotificationsClient
import domain.dto.Notification
import domain.dto.StorableConfirmationNotification
import domain.repositories.OtpCodeRepository
import exceptions.NotificationNotFoundException
import exceptions.OtpCodeException
import utils.ConfirmationUtils.generateCode
import utils.PhoneNumberUtils
import utils.PhoneNumberUtils.areNumbersSame


class SmsMessageService(
    private val otpCodeRepository: OtpCodeRepository,
    private val smsNotificationsClient: SmsNotificationsClient,
    private val config: Config,
) {
    suspend fun sendConfirmNotification(userId: Long, notificationRequest: NotificationRequest) {
        val receiverPhoneNumber =
            PhoneNumberUtils.validateAndNormalizePhoneNumber(notificationRequest.receiverPhoneNumber)
        if (areNumbersSame(config.testPhoneNumber, receiverPhoneNumber)) {
            otpCodeRepository.save(
                userId,
                StorableConfirmationNotification(receiverPhoneNumber, config.testOtpCode)
            )
            return
        }
        val otpCode = generateCode()

        smsNotificationsClient.sendNotification(
            Notification(
                receiverPhoneNumber = receiverPhoneNumber,
                message = "Ваш код подтверждения $otpCode. Не говорите его никому."
            )
        )

        otpCodeRepository.save(
            userId,
            StorableConfirmationNotification(receiverPhoneNumber, otpCode)
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