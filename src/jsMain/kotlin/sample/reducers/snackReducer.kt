package sample.reducers

import sample.*

interface Reducer {
    fun call(state: List<*>, action: Action): List<*>
}

class snackReducer : Reducer {
    override fun call(state: List<*>, action: Action): List<*> {
        if(state[0] is Snack && action is snackAction ) {
            state as List<Snack>
            when (action.type) {
                ActionType.buyMars -> state[0].stock -= 1
                ActionType.buyTwix -> state[1].stock -= 1
                ActionType.buyBounty -> state[2].stock -= 1
                ActionType.refill -> for (snack in state) snack.stock += 10
            }
        }
        return state
    }
}

fun userReducer(state: List<User>, action: userAction) {
    println("Trying to perform : $action")
    when (action.type) {
        ActionType.refill -> state[0].balance += 10
    }
}