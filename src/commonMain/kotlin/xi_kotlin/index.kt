package xi_kotlin

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// Data
data class Snack(
    val ID: Int,
    var price: Int,
    var stock: Int,
    val name: String
)
data class User(
    val ID: Int,
    val name: String,
    var balance: Int
)

// Actions
sealed class Action
class addSnack(price: Int, stock: Int, name: String): Action() {
    val ID = ++snackID
    val price = price
    val stock = stock
    val name = name

}
object buySnack : Action()
object refill : Action()

// Generics
typealias Reducer<S, A> = (S, A) -> S
typealias Listener = suspend () -> Unit

class Store<S, A>(reducer: Reducer<S, A>, initialState: S) {
    private var currentReducer = reducer
    private var currentState = initialState
    private var isDispatching = false
    private var listeners: MutableList<Listener> = mutableListOf<Listener>()


    fun getState(): S {
        return currentState
    }

    fun subscribe(listener:Listener) {
        if(isDispatching) { throw Error("You may not subscribe when the store is dispatching.") }
        listeners.apply { this.add(listener)}
    }

    fun dispatch(action: A) {
        try {
            isDispatching = true
            currentState = currentReducer(currentState, action)
        } finally {
            isDispatching = false
        }

        for(listener in listeners) {
            GlobalScope.launch { listener() }
            println("launched a boi")
        }
    }

}

// Global initial data
var snackID = 0
var selectedSnack = Snack(99,99,99, "Non-existant")
val selectedUser = User(1, "henk", 50)

//Exception
class NoStockException(message: String) : Exception(message)
class NoMoneyException(message: String) : Exception(message)