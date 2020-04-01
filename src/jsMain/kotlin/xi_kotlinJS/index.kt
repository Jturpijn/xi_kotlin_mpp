package xi_kotlinJS

import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import xi_kotlinJS.reducers.snackReducer
import xi_kotlin.*
import xi_kotlinJS.Coroutines.snackRoutine

// Generating Initial state
class state(
    InitialSnackList: MutableList<Snack> = mutableListOf<Snack>(
        Snack(0,1,10, "Mars")
    )

) {
    val snacks = InitialSnackList
}

fun main() {
    //initial store
    val store = Store(snackReducer, state())
    store.subscribe { snackRoutine() }

    //create User Interface
    val userButtons = document.create.div {
        button {
            +"Top up Balance with 10"; onClickFunction = { _ ->
            selectedUser.balance += 10
            log("Succesfully topped up ${selectedUser.name}'s balance. Balance is now €${selectedUser.balance}")
        }
        }
    }
    val snackButtons = document.create.div {
        id = "snackbuttons"
        for(snack in store.getState().snacks) {
            button {
                id = snack.name
                +"buy a ${snack.name}"; onClickFunction = { _ ->
                selectedSnack = snack
                log("selected snack: $selectedSnack")
                store.dispatch(buySnack)
            }
            }
        }
        button {
            id = "addsnack"
            + "Add snack"; onClickFunction = { _ ->
            store.dispatch(addSnack(1, 10, "Twix"))
        }
        }
    }
    val actionButtons = document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            for(snack in store.getState().snacks) {
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
            +"Refill snack"; onClickFunction = { _ ->
            store.dispatch(refill)
        }
        }
    }

    // Load user interface
    document.addEventListener("DOMContentLoaded", {
        document.getElementById("users")!!.appendChild(userButtons)
        document.getElementById("ktor-request")!!.appendChild(snackButtons)
        document.getElementById("ktor-request")!!.appendChild(actionButtons)
    })
}