package xi_kotlinJS.reducers

import xi_kotlinJS.*
import xi_kotlin.*

val snackReducer = { s: State, a: Action ->
    when (a) {
        is addSnack -> State(s.snacks.apply { this.add(Snack(a.ID, a.price, a.stock, a.name)) })
        is buySnack -> State(s.snacks.apply { this[selectedSnack.ID].stock -= 1})
        is refill -> State(s.snacks.apply { for(snack in this) snack.stock += 10 })
    }
}