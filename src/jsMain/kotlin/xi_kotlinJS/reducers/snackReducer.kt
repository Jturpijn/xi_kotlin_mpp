package xi_kotlinJS.reducers

import xi_kotlinJS.*
import xi_kotlin.*

val snackReducer = { s: state, a: Action ->
    when (a) {
        is addSnack -> state(s.snacks.apply { this.add(Snack(a.ID, a.price, a.stock, a.name)) })
        is buySnack -> state(s.snacks.apply { this[selectedSnack.ID].stock -= 1})
        is refill -> state(s.snacks.apply { this[selectedSnack.ID].stock += 10 })
    }
}