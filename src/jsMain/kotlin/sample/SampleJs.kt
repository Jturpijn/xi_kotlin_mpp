package sample

import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.*
import kotlinx.html.js.*

fun main() {
    val snackButtons = document.create.div {
        for (snack in Store) {
            button {
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                document.getElementById("ktor-response")?.textContent =
                    "${buySnackByID(Fred, snack.ID)}!"
            }
            }
        }
    }
    val actionButtons = document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            document.getElementById("ktor-response")?.textContent = "the store : $Store"
        }
        }
        button {
            +"Show Balance"; onClickFunction = { _ ->
            document.getElementById("ktor-response")?.textContent = "Your profile : $Fred"
        }
        }
        button {
            +"refill"; onClickFunction = { _ ->
            document.getElementById("ktor-response")?.textContent = "the store : ${refillSnackStock(1, 5)}"
        }
        }
    }

    document.addEventListener("DOMContentLoaded", {
        document.getElementById("ktor-request")!!.appendChild(snackButtons)
        document.getElementById("ktor-request")!!.appendChild(actionButtons)
    })
}