package sample.sagas

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sample.*
import kotlin.js.Date
import kotlin.random.Random



suspend fun buySnack(action: snackAction) {
    delay((Random.nextLong(1, 9) * 1000))
    snackStore.transactions += 1
    when (action.type) {
         ActionType.buyMars -> try {
//            executeTransaction(selectedUser.ID, action.snack.ID);
            log("${Date.now()} finished $action result : ")
        } catch (e: Exception) {
            log("Unresolved: ${e.message}")
        }
        else -> snackStore
    }
}

fun executeTransaction(userID: Int, snackID: Int) {
    val user = userStore.users[userID] ; val snack = snackStore.snacks[snackID]
    if(snack.stock <= 0) throw NoStockException("Sorry, there are no ${snack.name} left in stock.")
    if(user.balance < snack.price) throw NoMoneyException("Please Top-Up your balance of €${user.balance} to buy this €${snack.price} snack.")
    user.balance -= snack.price ; --snack.stock
}