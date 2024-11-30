package domain.clients

import domain.dto.Notification


class SmsAeroNotificationsClient(
    private val client: SmsAeroClient
) : SmsNotificationsClient {
    override suspend fun sendNotification(notification: Notification) {
        client.send(notification.receiverPhoneNumber, notification.message, "SMS Aero")
    }
}