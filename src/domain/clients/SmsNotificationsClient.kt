package domain.clients

import domain.dto.Notification


interface SmsNotificationsClient {
    suspend fun sendNotification(notification: Notification)
}