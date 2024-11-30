package controllers

import controllers.request.NotificationRequest
import controllers.request.OtpConfirmationRequest
import domain.services.SmsMessageService

class SmsConfirmationController(private val smsMessageService: SmsMessageService) {
    suspend fun send(userId: Long, notificationRequest: NotificationRequest) =
        smsMessageService.sendConfirmNotification(userId, notificationRequest)

    fun confirm(userId: Long, otpConfirmationRequest: OtpConfirmationRequest) =
        smsMessageService.verifyOtpCode(userId, otpConfirmationRequest)
}