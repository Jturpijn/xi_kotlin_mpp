package sample

import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import kotlin.dom.appendText

val topUpButton = document.create.button {
    +"Top up Balance with 10"; onClickFunction = { _ ->
    selectedUser.balance += 10
    document.getElementById("ktor-response")?.textContent =
        "You've succesfully topped up your balance. Your new balance is €${selectedUser.balance}"
}
}

fun log(message: String) {
    document.getElementById("log")!!.appendChild(document.create.div{p { +message }})
}

fun main() {
    val userButtons = document.create.div {
        for (user in userStore) {
            button {
                +"select user: ${user.name}"; onClickFunction = { _ ->
                selectedUser = user
                document.getElementById("selected-user")?.textContent = "Selected user : ${user.name} with balance : €${user.balance}"
            }
            }
        }
    }
    val snackButtons = document.create.div {
        for (snack in snackStore) {
            button {
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                document.getElementById("ktor-response")?.textContent =
                    "${buySnackByID(selectedUser.ID, snack.ID)}!"
            }
            }
        }
    }
    val actionButtons = document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            document.getElementById("ktor-response")?.textContent = "the store : $snackStore"
        }
        }
        button {
            +"Show Balance"; onClickFunction = { _ ->
            document.getElementById("ktor-response")?.textContent =
                "Hello ${selectedUser.name}, your current balance is €${selectedUser.balance}"
            document.getElementById("ktor-response")!!.appendChild(topUpButton)
        }
        }
        button {
            +"Refill all snacks"; onClickFunction = { _ ->
            for (snack in snackStore) {
                document.getElementById("ktor-response")?.textContent = "the store : ${refillSnackStock(snack.ID, 10)}"
            }
        }
        }
    }


    document.addEventListener("DOMContentLoaded", {
        document.getElementById("users")!!.appendChild(userButtons)
        document.getElementById("ktor-request")!!.appendChild(snackButtons)
        document.getElementById("ktor-request")!!.appendChild(actionButtons)
    })
}