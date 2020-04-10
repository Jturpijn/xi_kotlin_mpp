package xi_kotlinJS.components

import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import xi_kotlin.selectedUser
import xi_kotlinJS.log
import kotlin.browser.document


//create User Interface
val userButtons = {document.create.div {
    button {
        +"Top up Balance with 10"; onClickFunction = { _ ->
        selectedUser.balance += 10
        log("Succesfully topped up ${selectedUser.name}'s balance. Balance is now €${selectedUser.balance}")
    }
    }
}}