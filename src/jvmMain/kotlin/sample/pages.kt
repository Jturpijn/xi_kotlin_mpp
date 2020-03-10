package sample

import io.ktor.html.Template
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
                }
                div {
                    id = "ktor-request"
                }
                div {
                    id = "selected-user"
                    +"Selected user : ${selectedUser.name}"
                }
                div {
                    id = "logs"
                }
            }
            script(src = "/static/xi_kotlin.js") {}
        }
    }
}