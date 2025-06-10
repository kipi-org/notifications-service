import controllers.SmsConfirmationController
import domain.clients.SmsAeroClient
import domain.clients.SmsAeroNotificationsClient
import domain.repositories.CacheOtpCodeRepository
import domain.services.SmsMessageService
import net.spy.memcached.AddrUtil
import net.spy.memcached.ConnectionFactoryBuilder
import net.spy.memcached.MemcachedClient

class Dependencies {
    private val config = Config()

    private val memcachedClient = MemcachedClient(
        ConnectionFactoryBuilder()
            .setOpTimeout(config.memcachedTimeoutMs)
            .build(),
        AddrUtil.getAddresses(config.memcachedUrl),
    )
    private val cacheOtpCodeRepository = CacheOtpCodeRepository(memcachedClient)
    private val smsNotificationsClient =
        SmsAeroNotificationsClient(SmsAeroClient(config.smsAeroClientEmail, config.smsAeroClientApiKey))
    private val smsMessageService = SmsMessageService(cacheOtpCodeRepository, smsNotificationsClient, config)

    val smsConfirmationController = SmsConfirmationController(smsMessageService)
}