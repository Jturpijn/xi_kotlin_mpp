package xi_kotlinJVM

import xi_kotlin.generics.*

sealed class Actions
class Plus : Actions()

fun main() {

    val countReducer: Reducer<Int, Actions> = { i: Int, a: Actions ->
        i + 1
    }

    val root = ReducerStore(countReducer, 0)

    val logger: Middleware<Int, Actions> = { store, action, next ->
        println("Before: ${store.getState()}")
        next(action)
        println("After: ${store.getState()}")
    }

    val store = applyAll(root, logger, logger, logger, logger, logger, logger, logger, logger, logger, logger)
    store.dispatch(Plus())
    store.dispatch(Plus())
    store.dispatch(Plus())

}