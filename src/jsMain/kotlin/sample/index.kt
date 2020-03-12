package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import sample.sagas.buySnack

val logs = mutableListOf<String>()
fun log(message: String) {
    logs.add(message)
    if (document.getElementById("logs")!!.hasChildNodes()) {
        document.getElementById("logs")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(document.create.div { id = "logs" })
    }
    for (log in logs.asReversed()) {
        document.getElementById("logs")!!.appendChild(document.create.p {
            id = "log"
            +log
        })
    }
}

// Channel
fun actionWatcher() = GlobalScope.launch {
    while (true) {
        for (action in reducerChannel) {
            log("dispatched $action")
            buySnack(action)
        }
    }
}

fun dispatch(action: String) = GlobalScope.launch {
    log("dispatching $action")
    when (action) {
        "buyMars" -> reducerChannel.send(Action.buyMars)
        "buyTwix" -> reducerChannel.send(Action.buyTwix)
        "buyBounty" -> reducerChannel.send(Action.buyBounty)
    }
}

fun main() {
    actionWatcher()
    val userButtons = document.create.div {
        button {
            type = ButtonType.submit
            +"Top up Balance with 10"; onClickFunction = { _ ->
            selectedUser.balance += 10
            log("Succesfully topped up ${selectedUser.name}'s balance. Balance is now €${selectedUser.balance}")
        }
        }
        for (user in userStore.users) {
            button {
                type = ButtonType.submit
                +"select user: ${user.name}"; onClickFunction = { _ ->
                selectedUser = user
                log("Selected user : ${user.name} with balance : €${user.balance}")
            }
            }
        }
    }
    val snackButtons = document.create.div {
        for (snack in snackStore.snacks) {
            button {
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                id = "${snack.ID}"
                dispatch("buy${snack.name}")
            }
            }
        }
    }
    val actionButtons = document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            log("the store : $snackStore")
        }
        }
        button {
            +"Show Balance"; onClickFunction = { _ ->
            log("Hello ${selectedUser.name}, your current balance is €${selectedUser.balance}")
        }
        }
        button {
            +"Refill all snacks"; onClickFunction = { _ ->
            dispatch("refillSnacks")
        }
        }
    }

    document.addEventListener("DOMContentLoaded", {
        document.getElementById("users")!!.appendChild(userButtons)
        document.getElementById("ktor-request")!!.appendChild(snackButtons)
        document.getElementById("ktor-request")!!.appendChild(actionButtons)
    })
}