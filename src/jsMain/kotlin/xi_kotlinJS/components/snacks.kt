package xi_kotlinJS.components

import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.id
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import xi_kotlin.*
import xi_kotlinJS.log
import xi_kotlinJS.store
import kotlin.browser.document


val refreshSnacks ={ document.create.div {
    id = "snackbuttons"
    for(snack in store.getState().snacks) {
        button {
            id = "${snack.ID}${snack.name}"
            +"buy a ${snack.name}"; onClickFunction = { _ ->
            selectedSnack = snack
            log("selected snack: $id")
            store.dispatch(buySnack)
        }
        }
    }
    button {
        id = "addsnack"
        + "Add snack"; onClickFunction = { _ ->
        store.dispatch(addSnack(1,10,"Twix"))
    }
    }
}}