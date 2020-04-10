package xi_kotlinJS.components

import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import xi_kotlin.refill
import xi_kotlin.selectedUser
import xi_kotlinJS.log
import xi_kotlinJS.store
import kotlin.browser.document


val refreshAction = {
    document.create.div {
        button {
            +"Show Stock"; onClickFunction = { _ ->
            for(snack in store.getState().snacks) {
                log("ID: ${snack.ID} | ${snack.name} \t | #${snack.stock} | €${snack.price}")
            }
        }
        }
        button {
            +"Show Balance"; onClickFunction = { _ ->
            log("Hello ${selectedUser.name}, your current balance is €${selectedUser.balance}")
        }
        }
        button {
            +"Refill snack"; onClickFunction = { _ ->
            store.dispatch(refill)
        }
        }
    }}