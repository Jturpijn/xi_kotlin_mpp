package sample

import io.ktor.application.ApplicationCall
import io.ktor.html.Template
import io.ktor.http.ContentType
import io.ktor.response.respondText
import kotlinx.html.*


class VendingMachineTemplate : Template<HTML> {
    override fun HTML.apply() {
        head {
            title("Vending machine POC")
        }
        body {
            div {
                id = "container"
                div {
                    id = "users"
                    div {
                        id = "selected-user"
                        +"Selected user : ${selectedUser.name}"
                    }
                }
                div {
                    id = "ktor-request"
                    +" "
                }
                div {
                    id = "ktor-response"
                    +" "
                }

                div {
                    id = "log"
                    +"Log :   "
                }
            }
            script(src = "/static/xi_kotlin.js") {}
        }
    }
}