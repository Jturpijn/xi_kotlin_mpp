package sample

import kotlinx.coroutines.*
import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.Text
import org.w3c.dom.get
import kotlin.dom.appendElement
import kotlin.dom.appendText
import kotlin.js.Date
import kotlin.js.Promise
import kotlin.random.Random

val topUpButton = document.create.button {
    +"Top up Balance with 10"; onClickFunction = { _ ->
    selectedUser.balance += 10
    log("Succesfully topped up ${selectedUser.name}'s balance. Balance is now €${selectedUser.balance}")
}
}

val logs = mutableListOf<String>()
fun log(message: String) {
    logs.add(message)
    if(document.getElementById("logs")!!.hasChildNodes()) {
        document.getElementById("logs")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(document.create.div { id = "logs"})
    }
    for(log in logs.asReversed()) {
        document.getElementById("logs")!!.appendChild(document.create.p { id= "log"
                                                                                        +log })
    }
}

fun getAsync(action: Action, user: User, snack: Snack): Promise<Any> = GlobalScope.promise {
    rootSaga(action, user, snack, Random.nextInt(1, 9))
}

fun main() {
    val userButtons = document.create.div {
        document.getElementById("ktor-request")!!.appendChild(topUpButton)
        for (user in userStore) {
            button {
                +"select user: ${user.name}"; onClickFunction = { _ ->
                selectedUser = user
                log("Selected user : ${user.name} with balance : €${user.balance}")
            }
            }
        }
    }
    val snackButtons = document.create.div {
        var count = 0
        for (snack in snackStore) {
            button {
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                val named = "${count++}${snack.name}"
                log("clicked $named")
                getAsync(Action.BuySnackByID, selectedUser, snack).then {
                    log("resolved $named for ${snack.name} result : $it")
                }.catch {
                    log("$it")
                }
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
            for (snack in snackStore) {
                refillSnackStock(snack.ID, 10)
                log("Succesfully restocked ${snack.ID} snacks by 10")
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