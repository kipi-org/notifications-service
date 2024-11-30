package exceptions

class NotificationNotFoundException(override val message: String) : RuntimeException(message)