package sample

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

var snackStore = InitialSnackStore
var userStore = InitialUserStore
var selectedUser: User = userStore[0]

// Retrieving data
fun getsnackIDByName(snackName: String) = when (snackName) {
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
    if(getSnackStockByID(snackID) <= 0) return "Sorry, there are no ${snack.name} left in stock."
    if(user.balance >= snack.price) executeTransaction(userID, snackID)
    else return "Please Top-Up your balance of €${user.balance} to buy this €${snack.price} snack."
    return "You've succesfully bought a ${snack.name}. Your remaining balance becomes €${user.balance}"
}
fun executeTransaction(userID: Int, snackID: Int) {
    userStore[userID].balance -= snackStore[snackID].price ; --snackStore[snackID].stock
}

// Manipulating users

