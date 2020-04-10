package xi_kotlinJS

import xi_kotlin.Action
import xi_kotlin.Snack
import kotlin.browser.*
import xi_kotlinJS.reducers.snackReducer
import xi_kotlin.generics.*
import xi_kotlinJS.components.refreshAction
import xi_kotlinJS.components.refreshSnacks
import xi_kotlinJS.components.userButtons
import xi_kotlinJS.coroutines.snackRoutine

// Generating Initial state
data class State(
    val snacks: MutableList<Snack> = mutableListOf(
        Snack(0, 1, 10, "Mars")
    )
)
lateinit var store: Store<State, Action, Action>

fun main() {
    //initial store
    store = createStore(snackReducer, State(), applyMiddleware(sagaMiddlewareFactory()))
    store.subscribe { snackRoutine() }


    // Load user interface
    document.addEventListener("DOMContentLoaded", {
        document.getElementById("actions")!!.appendChild(userButtons())
        document.getElementById("actions")!!.appendChild(refreshAction())
        document.getElementById("users")!!.appendChild(refreshSnacks())
    })
}