package sample

import kotlinx.coroutines.*

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
enum class Action() {
    GetSnackStore,
    GetUserStore,
    GetSnackByName,
    GetSnackByID,
    BuySnackByID,
    GetUserBalance
}

// Stores
val InitialSnackStore = mutableListOf<Snack>(
    Snack(0,1,10, "Mars"),
    Snack(1,1,10, "Twix"),
    Snack(2,2,10, "Bounty")
)
val InitialUserStore = mutableListOf<User>(
    User(0,"Fred", 20),
    User(1,"Bert", 30),
    User(2,"Henk", 25)
)

// Generating Initial state
var snackStore = InitialSnackStore
var userStore = InitialUserStore
var selectedUser: User = userStore[0]

// Retrieving data
fun getsnackIDByName(snackName: String):Any = when (snackName) {
        "Mars" -> 0
        "Twix" -> 1
        "Bounty" -> 2
        else -> "No Snack in inventory with the snackname : $snackName"
    }
fun getSnackNameByID(snackID: Int) = snackStore[snackID].name
fun getSnackPriceByID(snackID: Int) = snackStore[snackID].price
fun getSnackStockByID(snackID: Int) = snackStore[snackID].stock

// Manipulating snacks
fun refillSnackStock(snackID: Int, refill: Int): Int { snackStore[snackID].stock += refill ; return snackStore[snackID].stock}
fun buySnackByID(userID: Int, snackID: Int): String  {
    val user = userStore[userID]
    val snack = snackStore[snackID]
    if(getSnackStockByID(snackID) <= 0) throw noStockException("Sorry, there are no ${snack.name} left in stock.")
    if(user.balance >= snack.price) executeTransaction(userID, snackID)
    else throw noMoneyException("Please Top-Up your balance of €${user.balance} to buy this €${snack.price} snack.")
    return "You've succesfully bought a ${snack.name}. Your remaining balance becomes €${user.balance}"
}
fun executeTransaction(userID: Int, snackID: Int) {
    userStore[userID].balance -= snackStore[snackID].price ; --snackStore[snackID].stock
}

// Coroutines
fun runSaga(action: Action, user: User, snack: Snack):Any = when (action) {
    Action.GetSnackStore -> snackStore
    Action.GetUserStore -> userStore
    Action.GetSnackByName -> getsnackIDByName(snack.name)
    Action.GetSnackByID -> getSnackNameByID(snack.ID)
    Action.BuySnackByID -> buySnackByID(user.ID, snack.ID)
    Action.GetUserBalance -> userStore[user.ID].balance
}
suspend fun rootSaga(action: Action, user: User, snack: Snack, delay: Int) = coroutineScope {
        delay((delay * 1000).toLong())
        val response = async { runSaga(action, user, snack) }
        response.await()
    }

class noStockException(message: String) : Exception(message)
class noMoneyException(message: String) : Exception(message)