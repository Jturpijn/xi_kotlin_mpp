package xi_kotlinJS

import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import xi_kotlinJS.reducers.snackReducer
import xi_kotlin.*
import xi_kotlinJS.coroutines.snackRoutine

// Generating Initial state
data class state(
    val snacks: MutableList<Snack> = mutableListOf(Snack(0,1,10, "Mars"))
)
lateinit var store: Store<state, Action>
var refreshSnacks ={ document.create.div {
    id = "snackbuttons"
    for(snack in store.getState().snacks) {
        button {
            id = "${snack.ID}${snack.name}"
            +"buy a ${snack.name}"; onClickFunction = { _ ->
            selectedSnack = snack
            log("selected snack: $id")
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
}}
var refreshAction = {document.create.div {
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
}}

fun main() {
    //initial store
    store = Store(snackReducer, state())
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

    // Load user interface
    document.addEventListener("DOMContentLoaded", {
        document.getElementById("users")!!.appendChild(userButtons)
        document.getElementById("users")!!.appendChild(refreshSnacks())
        document.getElementById("container")!!.appendChild(refreshAction())
    })
}