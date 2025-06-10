class Config {
    private val envs = System.getenv()

    val smsAeroClientEmail = getEnv("SMS_AERO_EMAIL")
    val smsAeroClientApiKey = getEnv("SMS_AERO_API_KEY")
    val testPhoneNumber = getEnv("TEST_PHONE_NUMBER")
    val testOtpCode = envs["TEST_OTP_CODE"] ?: "0000"
    val memcachedUrl = envs["MEMCACHED_URL"] ?: "localhost:11211"
    val memcachedTimeoutMs = (envs["MEMCACHED_TIMEOUT_MS"] ?: "100").toLong()

    private fun getEnv(name: String): String = envs[name] ?: throw RuntimeException("Env $name not exist")
}
