package xi_kotlinJS.Coroutines

import kotlinx.coroutines.delay
import xi_kotlinJS.log
import xi_kotlinJS.state

var currentState: state = state()
suspend fun snackRoutine() {
    delay(1000)
    val previousState = currentState
    currentState = state()

        if(previousState != currentState) {
            log("The state has changed!")
        }
}