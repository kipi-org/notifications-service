package exceptions

class OtpCodeException(override val message: String) : RuntimeException(message)