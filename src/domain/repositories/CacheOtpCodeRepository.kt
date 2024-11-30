package domain.repositories

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import domain.dto.StorableConfirmationNotification
import java.time.Duration

class CacheOtpCodeRepository(
    private val cache: Cache<Long, StorableConfirmationNotification> = CacheBuilder
        .newBuilder()
        .expireAfterWrite(Duration.ofMinutes(10))
        .build()
) : OtpCodeRepository {

    override fun save(userId: Long, notification: StorableConfirmationNotification) {
        cache.invalidate(userId)
        cache.put(userId, notification)
    }

    override fun get(userId: Long): StorableConfirmationNotification? = cache.getIfPresent(userId)

    override fun delete(userId: Long) = cache.invalidate(userId)
}