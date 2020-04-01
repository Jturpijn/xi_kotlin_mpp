package xi_kotlinJS.coroutines

import kotlinx.coroutines.delay
import xi_kotlin.Snack
import xi_kotlinJS.*
import kotlin.browser.document

var currentState = mutableListOf<Snack>()
var previousState = mutableListOf<Snack>()

suspend fun snackRoutine() {
    previousState.apply { clear() ; addAll(currentState) }
    currentState.apply { clear() ; addAll(store.getState().snacks) }

    log("previous ${previousState.size} and current ${currentState.size}")
    delay(100)

    if(previousState.size < currentState.size) {
        document.getElementById("snackbuttons")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(refreshSnacks())
        log("A snack has been added!")
    }

}