package xi_kotlin.generics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xi_kotlin.Action

// Types
typealias Reducer<S, A> = (S, A) -> S
typealias Listener = () -> Unit
typealias Middleware = () -> Unit
typealias Enhancer<S> = (Reducer<S,Action>, S)-> Store<S, Action, Action>

/*
 * The default layout of a store that stores data and applies actions in order to manipulate that data.
 */
interface Store<S, A, R> {
    var currentReducer: Reducer<S,A>
    var currentState: S
    var isDispatching: Boolean
    var listeners: MutableList<Listener>

    fun getState(): S {
        return currentState
    }

    fun subscribe(listener: Listener) {
        if (isDispatching) {
            throw Error("You may not subscribe when the store is dispatching.")
        }
        listeners.apply { this.add(listener) }
    }

    fun dispatch(action: A) {
        try {
            isDispatching = true
            currentState = currentReducer(currentState, action)
        } finally {
            isDispatching = false
        }

        for (listener in listeners) {
            GlobalScope.launch { listener() }
        }
    }

}

/*
 * The createStore functions build a Store which provides data and a way to manipulate this data. The first function
 * takes only a reducer and initial state to build a 'simple' store.
 *
 * @param {Function} Reducer<S,A> A function that takes a change and returns a new state
 * @param {Any} State<S> Data that holds the data which hydrates the store.
 *
 * @return {Store} Store <S, Action, Action> A store which holds data and functions to manipulate the data.
 */
fun <S> createStore(reducer: Reducer<S, Action>, initialState: S): Store<S, Action, Action> {
    class snackStore: Store<S,Action,Action> {
        override var currentReducer = reducer
        override var currentState = initialState
        override var isDispatching = false
        override var listeners: MutableList<Listener> = mutableListOf()
    }

    return snackStore()
}

/*
 * This second createStore function takes a third parameter which applies 'third-party' middleware libraries. The function
 * returns an 'Enhancer<S>' which returns a store which wraps the getState and dispatch functions from the Store in order
 * to provide an improved Store. The default enhancer which has been provided with this implementation is applyMiddleware()
 *
 * @param {Function} Reducer<S,A> A function that takes a change and returns a new state
 * @param {Any} State<S> Data that holds the data which hydrates the store.
 * @param {Function} Enhancer<S> A function that integrates with the store to apply  third party logic
 *
 * @return {Store} Store <S, Action, Action> A store which holds data and functions to manipulate the data.
 */
fun <S> createStore(
    reducer: Reducer<S, Action>,
    initialState: S,
    enhancer: Enhancer<S>
): Store<S, Action, Action> {
    return enhancer(reducer, initialState)
}

/*
 * applyMiddleware creates an enhancer function which takes a reducer: Reducer<S,A> and a state <S> in order to create
 * an enhanced store with integrated middlewares.
 *
 * 10-04-2020 - This first prototype creates a new store and has not yet implemented middlewares.
 *
 * @param {Function} Reducer<S,A> A function that takes a change and returns a new state
 * @param {Any} State<S> Data that holds the data which hydrates the store.
 *
 * @return {Store} Store <S, Action, Action> A store which holds data and functions to manipulate the data.
 */
fun <S> applyMiddleware(vararg middlewares: Middleware): (Reducer<S, Action>, S) -> Store<S, Action, Action> {
    return { r: Reducer<S, Action>, s: S ->
        class storage : Store<S, Action, Action> {
            override var currentReducer = r
            override var currentState = s
            override var isDispatching = false
            override var listeners: MutableList<Listener> = mutableListOf()

            override fun getState(): S {
                return super.getState()
            }

            override fun dispatch(action: Action) {
                super.dispatch(action)
            }
        }

        val middlewareAPI = object {
            val getState = storage::getState
            val dispatch = storage::dispatch
        }

        storage()
    }
}