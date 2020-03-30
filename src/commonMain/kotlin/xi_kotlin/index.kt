package xi_kotlin

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
class Store<S, A>(private val reducer: Reducer<S, A>, initialState: S) {
    var currentState = initialState

    fun getState(): S {
        return currentState
    }

    fun dispatch(action: A) {
        this.currentState = this.reducer(currentState, action)
    }

}

// Global data
var snackID = 0
var selectedSnack = Snack(99,99,99, "Non-existant")
val selectedUser = User(1, "henk", 50)

//Exception
class NoStockException(message: String) : Exception(message)
class NoMoneyException(message: String) : Exception(message)