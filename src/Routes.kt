import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import controllers.request.NotificationRequest
import controllers.request.OtpConfirmationRequest

fun Application.routes(deps: Dependencies) = with(deps) {
    routing {
        get("/health") {
            call.respond(OK)
        }

        route("/users/{userId}") {
            route("/send") {
                route("/confirmation") {
                    post<NotificationRequest> {
                        smsConfirmationController.send(call.userId, it)

                        call.respond(OK)
                    }

                    put<OtpConfirmationRequest> {
                        smsConfirmationController.confirm(call.userId, it)

                        call.respond(OK)
                    }
                }
            }
        }
    }
}

private val ApplicationCall.userId: Long get() = this.parameters.getOrFail("userId").toLong()
