package sample


typealias Reducer<S, A> = (S, A) -> S

class Store<S, A>(private val reducer: Reducer<S, A>, initialState: S) {
    private var state = initialState

    fun dispatch(action: A) {
        this.state = this.reducer(state, action)

    }
}


class MyState(initial: Int = 0) {
    var counter = initial
}

sealed class MyActions {
    class Add : MyActions()
    class Sub : MyActions()
}


fun main() {

    val state = MyState()
    val myReducer: Reducer<MyState, MyActions> = { s: MyState, a: MyActions ->
        when (a) {
            is MyActions.Add -> MyState(s.counter + 1)
            is MyActions.Sub -> MyState(s.counter - 1)
        }
    }

    val store = Store(myReducer, state)

    store.dispatch(MyActions.Add())


}