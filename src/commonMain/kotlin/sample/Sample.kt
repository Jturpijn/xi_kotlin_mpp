package sample

data class Snack(
    val ID: Int,
    var price: Int,
    var stock: Int,
    val name: String
)
data class User(
    val name: String,
    var balance: Int
)
val InitialStore = mutableListOf<Snack>(
    Snack(0,1,10, "Mars"),
    Snack(1,1,10, "Twix"),
    Snack(2,2,10, "Bounty")
)

var Store = InitialStore
var Fred = User("Fred", 30)

fun getsnackIDByName(snackName: String) = when (snackName) {
        "Mars" -> 0
        "Twix" -> 1
        "Bounty" -> 2
        else -> "No Snack in inventory with the snackname : $snackName"
    }
fun getSnackNameByID(snackID: Int) = Store[snackID].name
fun getSnackPriceByID(snackID: Int) = Store[snackID].price
fun getSnackStockByID(snackID: Int) = Store[snackID].stock

fun refillSnackStock(snackID: Int, refill: Int): Int { Store[snackID].stock += refill ; return Store[snackID].stock}
fun buySnackByID(user: User, snackID: Int): String  {
    if(getSnackStockByID(snackID) <= 0) return "Sorry, there are no ${Store[snackID].name} left in stock."
    if(user.balance > Store[snackID].price) executeTransaction(user, snackID)
    else return "Please Top-Up your balance of €${user.balance} to buy this €${Store[snackID].price} snack."
    return "You've succesfully bought a ${Store[snackID].name}. Your remaining balance becomes €${user.balance}"
}

fun executeTransaction(user: User, snackID: Int) {
    user.balance -= Store[snackID].price ; --Store[snackID].stock
}