package sample

import kotlinx.coroutines.channels.Channel

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
interface Action {
    val type: ActionType
}
data class snackAction(
    override val type: ActionType
): Action
data class userAction(
    override val type: ActionType,
    val user: User = selectedUser
): Action
enum class ActionType {
    buyMars,
    buyTwix,
    buyBounty,
    refill
}
// Stores
data class SnackStore (
    val snacks: List<Snack>,
    var transactions: Int
)
data class UserStore(
    val users: List<User>
)

// Generating Initial state
var snackStore = SnackStore( snacks = mutableListOf(
        Snack(0,1,10, "Mars"),
        Snack(1,1,10, "Twix"),
        Snack(2,2,10, "Bounty")
    ), transactions = 0 )
val userStore = UserStore( users = mutableListOf(
        User(0, "Bert", 10),
        User(1, "Henk", 25),
        User(2, "Fred", 20)
    ) )
var selectedUser: User = userStore.users[0]
var reducerChannel = Channel<Action>()

// Retrieving data
//fun getsnackIDByName(snackName: String):Any = when (snackName) {
//        "Mars" -> 0
//        "Twix" -> 1
//        "Bounty" -> 2
//        else -> "No Snack in inventory with the snackname : $snackName"
//    }
//fun getSnackNameByID(snackID: Int) = snackStore[snackID].name
//fun getSnackPriceByID(snackID: Int) = snackStore[snackID].price

// Manipulating snacks
//fun refillSnackStock(snackID: Int, refill: Int): Int { snackStore[snackID].stock += refill ; return snackStore[snackID].stock}
//fun buySnackByID(userID: Int, snackID: Int): String  {
//    val user = userStore[userID]
//    val snack = snackStore[snackID]
//    if(getSnackStockByID(snackID) <= 0) throw NoStockException("Sorry, there are no ${snack.name} left in stock.")
//    if(user.balance >= snack.price) executeTransaction(userID, snackID)
//    else throw NoMoneyException("Please Top-Up your balance of €${user.balance} to buy this €${snack.price} snack.")
//    return "You've succesfully bought a ${snack.name}. Your remaining balance becomes €${user.balance}"
//}

class NoStockException(message: String) : Exception(message)
class NoMoneyException(message: String) : Exception(message)