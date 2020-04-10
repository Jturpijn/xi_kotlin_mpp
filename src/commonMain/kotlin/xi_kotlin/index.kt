package xi_kotlin

import kotlinx.coroutines.GlobalScope
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
class addSnack(val price: Int, val stock: Int, val name: String): Action() {
    val ID = ++snackID
}
object buySnack : Action()
object refill : Action()


// Global initial data
var snackID = 0
var selectedSnack = Snack(99,99,99, "Non-existant")
val selectedUser = User(1, "henk", 50)

//Exception
class NoStockException(message: String) : Exception(message)
class NoMoneyException(message: String) : Exception(message)