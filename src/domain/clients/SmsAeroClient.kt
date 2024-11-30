package domain.clients

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class SmsAeroClient(private val email: String, private val apiKey: String) {
    fun send(number: String, text: String, sign: String): JSONObject? {
        val data: MutableMap<String, String> = HashMap()
        data["number"] = number
        data["text"] = text
        data["sign"] = sign
        return doRequest("sms/send", data)
    }

    private fun getAuth(): String {
        val auth = "$email:$apiKey"
        return "Basic ${String(Base64.getEncoder().encode(auth.toByteArray()))}"
    }

    private fun getData(form: Map<String, String>): String {
        val json = JSONObject()

        form.entries.forEach {
            json[it.key] = it.value
        }

        return json.toString()
    }

    private fun doRequest(method: String, form: Map<String, String>): JSONObject? {
        var url: String
        val urlsLen = GATE_URLS.size
        for (i in 0 until urlsLen) {
            return try {
                url = GATE_URLS[i]
                doSendRequest(method, form, url)
            } catch (e: Exception) {
                if (urlsLen == i + 1) {
                    throw IOException(e.message)
                }
                continue
            }
        }

        return null
    }

    private fun doSendRequest(method: String, form: Map<String, String>, url: String): JSONObject? {
        val urlObj = URL(url + method)
        val con = urlObj.openConnection() as HttpURLConnection
        con.setRequestProperty("Authorization", getAuth())
        con.setRequestProperty("Content-Type", "application/json")
        con.addRequestProperty("User-Agent", "Mozilla/4.0")
        con.requestMethod = "POST"
        con.doOutput = true
        val os = con.outputStream
        val input = getData(form).toByteArray(charset("utf-8"))
        os.write(input, 0, input.size)
        os.flush()
        os.close()
        if (con.responseCode == HttpURLConnection.HTTP_OK) {
            val parser = JSONParser()
            val obj = parser.parse(BufferedReader(InputStreamReader(con.inputStream)))
            return obj as JSONObject
        }
        return null
    }

    companion object {
        private val GATE_URLS = listOf(
            "https://gate.smsaero.ru/v2/",
            "https://gate.smsaero.org/v2/",
            "https://gate.smsaero.net/v2/",
            "https://gate.smsaero.uz/v2/"
        )
    }
}