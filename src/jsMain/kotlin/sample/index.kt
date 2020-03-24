package sample

import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import sample.reducers.Reducer
import sample.reducers.snackReducer
import sample.reducers.userReducer
import kotlin.reflect.KFunction

val logs = mutableListOf<String>()
fun log(message: String) {
    logs.add(message)
    if (document.getElementById("logs")!!.hasChildNodes()) {
        document.getElementById("logs")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(document.create.div { id = "logs" })
    }
    for ((index, log) in logs.asReversed().withIndex()) {
        if(index > 20) break
        document.getElementById("logs")!!.appendChild(document.create.p {
            id = "log"
            +log
        })
    }
}


class Store(reducer: Reducer, state: List<*>) {
    private var currentState = state
    private var currentReducer = reducer //{action: Action -> Any()}
    private var isDispatching = false

    fun getState(): Any {
        return currentState
    }

    fun dispatch(action:Action) {
        if (isDispatching) log("Reducers cannot dispatch while another action is dispatching.")

        try {
            println("Trying to dispatch : $action on $currentState through $currentReducer")
            isDispatching = true
            currentState = currentReducer.call(currentState, action)
        } finally {
            isDispatching = false
        }
    }

}
fun <Reducer, Any> createStore(reducer: sample.reducers.Reducer, state: List<*>): Store {
    return Store(reducer = reducer, state = state)
}


fun main() {
    val store = createStore<Reducer, List<*>>(snackReducer(), snackStore.snacks)
    log("${store.getState()}")
    val userButtons = document.create.div {
        button {
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
                id = "${snack.ID}"
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                store.dispatch(snackAction(ActionType.buyMars))
            }
            }
        }
    }
    val actionButtons = document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            var snacks: List<Snack> = store.getState() as List<Snack>
            for(snack in snacks.asReversed()) {
                log("ID: ${snack.ID} | ${snack.name} \t | #${snack.stock} | €${snack.price}")
            }
        }
        }
        button {
            +"Show Balance"; onClickFunction = { _ ->
            log("Hello ${selectedUser.name}, your current balance is €${selectedUser.balance}")
        }
        }
        button {
            +"Refill all snacks"; onClickFunction = { _ ->
//            store.dispatch(snackAction(ActionType.refill))
        }
        }
    }

    document.addEventListener("DOMContentLoaded", {
        document.getElementById("users")!!.appendChild(userButtons)
        document.getElementById("ktor-request")!!.appendChild(snackButtons)
        document.getElementById("ktor-request")!!.appendChild(actionButtons)
    })
}