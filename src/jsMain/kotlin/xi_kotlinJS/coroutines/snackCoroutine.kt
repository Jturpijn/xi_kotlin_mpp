package xi_kotlinJS.coroutines

import kotlinx.coroutines.delay
import xi_kotlin.Snack
import xi_kotlinJS.*
import xi_kotlinJS.components.refreshSnacks
import kotlin.browser.document

var currentState = mutableListOf<Snack>()
var previousState = mutableListOf<Snack>()

fun snackRoutine() {
    previousState.apply { clear(); addAll(currentState) }
    currentState.apply { clear(); addAll(store.getState().snacks) }

    if (previousState.size < currentState.size) {
        document.getElementById("snackbuttons")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(refreshSnacks())
        log("A snack has been added!")
    }
}