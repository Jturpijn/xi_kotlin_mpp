package xi_kotlinJVM

import io.ktor.html.Template
import kotlinx.html.*
import xi_kotlin.selectedUser

class VendingMachineTemplate : Template<HTML> {
    override fun HTML.apply() {
        head {
            title("Vending machine POC")
        }
        body {
            div {
                id = "container"
                +"Selected user : ${selectedUser.name}"
                div {
                    id = "actions"
                }
                div {
                    id = "users"
                }
                div {
                    id = "logs"
                }
            }
            script(src = "/static/xi_kotlin.js") {}
        }
    }
}