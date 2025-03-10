import controllers.SmsConfirmationController
import domain.clients.SmsAeroClient
import domain.clients.SmsAeroNotificationsClient
import domain.repositories.CacheOtpCodeRepository
import domain.services.SmsMessageService

class Dependencies {
    private val config = Config()

    private val cacheOtpCodeRepository = CacheOtpCodeRepository()
    private val smsNotificationsClient =
        SmsAeroNotificationsClient(SmsAeroClient(config.smsAeroClientEmail, config.smsAeroClientApiKey))
    private val smsMessageService = SmsMessageService(cacheOtpCodeRepository, smsNotificationsClient, config)

    val smsConfirmationController = SmsConfirmationController(smsMessageService)
}