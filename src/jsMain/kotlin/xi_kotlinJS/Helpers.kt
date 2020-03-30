package xi_kotlinJS

import kotlinx.html.dom.create
import kotlinx.html.id
import kotlinx.html.js.div
import kotlinx.html.js.p
import kotlin.browser.document

// custom log function to output
val logs = mutableListOf<String>()
fun log(message: String) {
    logs.add(message)
    if (document.getElementById("logs")!!.hasChildNodes()) {
        document.getElementById("logs")!!.outerHTML = " "
        document.getElementById("container")!!.appendChild(document.create.div { id = "logs" })
    }
    for ((index, log) in logs.asReversed().withIndex()) {
        if (index > 20) break
        document.getElementById("logs")!!.appendChild(document.create.p {
            id = "log"
            +log
        })
    }
}
