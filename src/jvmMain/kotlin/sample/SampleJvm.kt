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
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.launch

data class Post(val snackID: Int,
                val name: String
)
data class Refill(val snackID: Int,
                  val refill: Int
)
data class Buy(val userID: Int,
               val snackID: Int
)

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
                call.respond(snackStore)
            }
//            // Get the stock of a snack
//            post("/checkSnack") {
//                val payload = call.receive<Post>()
//                call.respond(getSnackStockByID(payload.snackID))
//            }
//            // Get the name of a snack
//            post("/getSnackName") {
//                val payload = call.receive<Post>()
//                call.respond(getSnackNameByID(payload.snackID))
//            }
//            // Get the price of a snack
//            post("/getSnackPrice") {
//                val payload = call.receive<Post>()
//                call.respond(getSnackPriceByID(payload.snackID))
//            }
//            // Get snackID by name
//            post("/getSnackID") {
//                val payload = call.receive<Post>()
//                call.respond(getsnackIDByName(payload.name))
//            }
//
//            // Buy a snack (reduct 1 from snackstock)
//            post("/buySnack") {
//                val payload = call.receive<Buy>()
//                call.respond(buySnackByID(payload.userID, payload.snackID))
//            }
//            // Refill the stofk of a snack
//            post("/refillSnackStock") {
//                val payload = call.receive<Refill>()
//                call.respond(refillSnackStock(payload.snackID, payload.refill))
//            }

            static("/static") {
                resource("xi_kotlin.js")
            }
        }
    }.start(wait = true)
}