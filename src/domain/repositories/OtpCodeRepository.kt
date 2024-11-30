package domain.repositories

import domain.dto.StorableConfirmationNotification

interface OtpCodeRepository {
    fun save(userId: Long, notification: StorableConfirmationNotification)
    fun get(userId: Long): StorableConfirmationNotification?
    fun delete(userId: Long)
}