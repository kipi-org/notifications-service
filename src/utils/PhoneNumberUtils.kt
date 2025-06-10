package utils


object PhoneNumberUtils {
  private const val RUSSIAN_NUMBER_LENGTH = 11
  private const val PHONE_NUMBER_MIN_LENGTH = 10
  private const val PHONE_NUMBER_MAX_LENGTH = 15
  private val RUSSIAN_NUMBER_AVAILABLE_PREFIXES = listOf("79", "89")

  fun validateAndNormalizePhoneNumber(phoneNumber: String): String {
    val cleanPhone = removeRedundantSymbols(phoneNumber)

    return if (isRussianNum(cleanPhone)) {
      if (cleanPhone.length != RUSSIAN_NUMBER_LENGTH)
        throw RuntimeException("phoneNumber.russian.incorrect",)

      "7${cleanPhone.substring(1)}"
    } else {
      if (cleanPhone.length !in PHONE_NUMBER_MIN_LENGTH..PHONE_NUMBER_MAX_LENGTH)
        throw RuntimeException("phoneNumber.international.incorrect",)

      cleanPhone
    }
  }

  private fun isRussianNum(phoneNumber: String) =
    RUSSIAN_NUMBER_AVAILABLE_PREFIXES.any { prefix -> phoneNumber.startsWith(prefix) }

  fun areNumbersSame(first: String, second: String) =
    validateAndNormalizePhoneNumber(first) == validateAndNormalizePhoneNumber(second)

  private fun removeRedundantSymbols(phoneNumber: String) =
    phoneNumber.replace("\\D".toRegex(), "").trimStart { it == '0' }
}
