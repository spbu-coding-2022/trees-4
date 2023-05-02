package app

import app.model.bst.RBTree

fun main() {
    val a = RBTree<String>()
    a.add("1000")
    a.add("1000")
    a.add("1000")
    a.add("1000")
    a.add("1000")
    a.add("1000")
    a.add("1000")

    a.remove("")

    print(a.root)
}