
package app.model.bst.balancer


import app.model.bst.BSTree
import kotlin.test.Test
import kotlin.test.assertEquals

class BSTBalancerTest {

    @Test
    fun `test method 'add' in bs tree`() {
        val tree = BSTree<Int>()

        tree.add(5)
        tree.add(1)
        tree.add(7)
        tree.add(4)
        tree.add(6)
        tree.add(3)

        /*
             constructed tree:
                   5
                /    \
               1      7
                \    /
                 4  6
                /
               3
         */

        assertEquals(5, tree.root?.value)
        assertEquals(1, tree.root?.left?.value)
        assertEquals(4, tree.root?.left?.right?.value)
        assertEquals(3, tree.root?.left?.right?.left?.value)
        assertEquals(7, tree.root?.right?.value)
        assertEquals(6, tree.root?.right?.left?.value)
    }

    @Test
    fun `test method 'remove' in bs tree`() {
        val tree = BSTree<Int>()

        tree.add(5)
        tree.add(1)
        tree.add(7)
        tree.add(4)
        tree.add(6)
        tree.add(3)

        /*
             constructed tree:
                   5
                /    \
               1      7
                \    /
                 4  6
                /
               3
         */

        tree.remove(5)

        /*
             constructed tree:
                   6
                /    \
               1      7
                \
                 4
                /
               3
         */


        assertEquals(6, tree.root?.value)
        assertEquals(1, tree.root?.left?.value)
        assertEquals(4, tree.root?.left?.right?.value)
        assertEquals(7, tree.root?.right?.value)
        assertEquals(3, tree.root?.left?.right?.left?.value)
    }
}