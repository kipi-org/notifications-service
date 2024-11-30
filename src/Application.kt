import controllers.response.ErrorResponse
import exceptions.NotificationNotFoundException
import exceptions.OtpCodeException
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import utils.SerializeUtils.getJsonMapper

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "0.0.0.0", module = Application::init)
        .start(wait = true)
}

fun Application.init() {
    val mapper = getJsonMapper()

    install(ContentNegotiation) {
        register(Json, JacksonConverter(mapper))
    }

    install(StatusPages) {
        exception<NotificationNotFoundException> { call, cause ->
            call.respond(NotFound, ErrorResponse(cause.message))
        }

        exception<OtpCodeException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }
    }

    val deps = Dependencies()

    routes(deps)
}
