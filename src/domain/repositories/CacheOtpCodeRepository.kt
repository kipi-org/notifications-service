package domain.repositories

import domain.dto.StorableConfirmationNotification
import net.spy.memcached.MemcachedClient
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

class CacheOtpCodeRepository(
    private val cache: MemcachedClient,
) : OtpCodeRepository {

    override fun save(userId: Long, notification: StorableConfirmationNotification) {
        cache.delete(userId.toString())
        cache.set(userId.toString(), 5.minutes.toInt(DurationUnit.SECONDS), notification)
    }

    override fun get(userId: Long): StorableConfirmationNotification? = try {
        cache.get(userId.toString()) as StorableConfirmationNotification
    } catch (e: Throwable) {
        null
    }

    override fun delete(userId: Long) {
        cache.delete(userId.toString())
    }
}