class Config {
    private val envs = System.getenv()

    val smsAeroClientEmail = getEnv("SMS_AERO_EMAIL")
    val smsAeroClientApiKey = getEnv("SMS_AERO_API_KEY")
    val testPhoneNumber = getEnv("TEST_PHONE_NUMBER")

    private fun getEnv(name: String): String = envs[name] ?: throw RuntimeException("Env $name not exist")
}
