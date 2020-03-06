package sample

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.html.*

data class PostSnack (val Snack: Post ) {
    data class Post(val ID: Int,
                    val name: String,
                    val refill: Int
                    )
}
class VendingMachineTemplate: Template<HTML> {
    override fun HTML.apply() {
        head {
            title("Vending machine POC")
        }
        body {
            div {
                id = "ktor-request"
            }
            div {
                id = "ktor-response"
                + " "
            }
            script(src = "/static/xi_kotlin.js") {}
        }
    }
}
fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            }
        }
        routing {
            get("/") {
                call.respondHtmlTemplate(VendingMachineTemplate()) {

                }
            }
            // Get the entire store.
            get("/getStore") {
                call.respond(Store)
            }
            // Get the stock of a snack
            post("/checkSnack") {
                val payload = call.receive<PostSnack>()
                call.respond(getSnackStockByID(payload.Snack.ID))
            }
            // Get the name of a snack
            post("/getSnackName") {
                val payload = call.receive<PostSnack>()
                call.respond(getSnackNameByID(payload.Snack.ID))
            }
            // Get the price of a snack
            post("/getSnackPrice") {
                val payload = call.receive<PostSnack>()
                call.respond(getSnackPriceByID(payload.Snack.ID))
            }
            // Get snackID by name
            post("/getSnackID") {
                val payload = call.receive<PostSnack>()
                call.respond(getsnackIDByName(payload.Snack.name))
            }

            // Buy a snack (reduct 1 from snackstock)
            post("/buySnack") {
                val payload = call.receive<PostSnack>()
                call.respond(buySnackByID(Fred, payload.Snack.ID))
            }
            // Refill the stofk of a snack
            post("/refillSnackStock") {
                val payload = call.receive<PostSnack>()
                call.respond(refillSnackStock(payload.Snack.ID, payload.Snack.refill))
            }

            static("/static") {
                resource("xi_kotlin.js")
            }
        }
    }.start(wait = true)
}