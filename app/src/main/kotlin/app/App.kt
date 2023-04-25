package app

import app.model.bst.RBTree
import app.model.bst.node.RedBlackTreeNode
import app.model.repo.PostgresRepo
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.strategy.RBTreeStrategy
import org.jetbrains.exposed.sql.Database
import kotlin.random.Random

fun main() {
    val tree = RBTree<Int>()
    val rand = Random(0)
    val values = List(10000) { rand.nextInt(1000) }
    values.forEach {
        tree.add(it)
    }

    val saver = PostgresRepo(
        RBTreeStrategy<Int>(
            { SerializableValue(it.toString()) },
            { it.value.toInt() }
        ),
        Database.connect(
            "jdbc:postgresql://localhost:5432/",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "password"
        )
    )
    saver.save("tmp", tree)
    val a = saver.loadByVerboseName("tmp", ::RBTree)
    println(a.iterator().asSequence().toList() == values.sorted())
}

object TreePrinter {
    /**
     * Print a tree
     *
     * @param root
     * tree root node
     */
    fun <T : Comparable<T>, Node : RedBlackTreeNode<T>> print(root: Node?) {
        val lines: MutableList<List<String?>> = ArrayList()
        var level: MutableList<Node?> = ArrayList()
        var next: MutableList<Node?> = ArrayList()
        level.add(root)
        var nn = 1
        var widest = 0
        while (nn != 0) {
            val line: MutableList<String?> = ArrayList()
            nn = 0
            for (n in level) {
                if (n == null) {
                    line.add(null)
                    next.add(null)
                    next.add(null)
                } else {
                    val aa = "${n.value}${n.color}"
                    line.add(aa)
                    if (aa.length > widest) widest = aa.length
                    next.add(n.left as Node?)
                    next.add(n.right as Node?)
                    if (n.left != null) nn++
                    if (n.right != null) nn++
                }
            }
            if (widest % 2 == 1) widest++
            lines.add(line)
            val tmp = level
            level = next
            next = tmp
            next.clear()
        }
        var perpiece = lines[lines.size - 1].size * (widest + 4)
        for (i in lines.indices) {
            val line = lines[i]
            val hpw = Math.floor((perpiece / 2f).toDouble()).toInt() - 1
            if (i > 0) {
                for (j in line.indices) {

                    // split node
                    var c = ' '
                    if (j % 2 == 1) {
                        if (line[j - 1] != null) {
                            c = if (line[j] != null) '┴' else '┘'
                        } else {
                            if (j < line.size && line[j] != null) c = '└'
                        }
                    }
                    print(c)

                    // lines and spaces
                    if (line[j] == null) {
                        for (k in 0 until perpiece - 1) {
                            print(" ")
                        }
                    } else {
                        for (k in 0 until hpw) {
                            print(if (j % 2 == 0) " " else "─")
                        }
                        print(if (j % 2 == 0) "┌" else "┐")
                        for (k in 0 until hpw) {
                            print(if (j % 2 == 0) "─" else " ")
                        }
                    }
                }
                println()
            }

            // print line of numbers
            for (j in line.indices) {
                var f = line[j]
                if (f == null) f = ""
                val gap1 = Math.ceil((perpiece / 2f - f.length / 2f).toDouble()).toInt()
                val gap2 = Math.floor((perpiece / 2f - f.length / 2f).toDouble()).toInt()

                // a number
                for (k in 0 until gap1) {
                    print(" ")
                }
                print(f)
                for (k in 0 until gap2) {
                    print(" ")
                }
            }
            println()
            perpiece /= 2
        }
    }
}
