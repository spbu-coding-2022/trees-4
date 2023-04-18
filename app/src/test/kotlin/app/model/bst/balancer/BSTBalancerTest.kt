package app.model.bst.balancer

import app.model.bst.node.BinSearchTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals

class BSTBalancerTest {
    private val balancer = BSBalancer<Int>()

    @Test
    fun `test method 'add' in bs balancer`() {
        var root = balancer.add(null, 5, true)

        root = balancer.add(root, 1, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 3, true)

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

        assertEquals(5, root.value)
        assertEquals(1, root.left?.value)
        assertEquals(4, root.left?.right?.value)
        assertEquals(3, root.left?.right?.left?.value)
        assertEquals(7, root.right?.value)
        assertEquals(6, root.right?.left?.value)
    }

    @Test
    fun `test method 'remove' in bs balancer`() {
        var root: BinSearchTreeNode<Int>? = balancer.add(null, 5, true)

        root = balancer.add(root, 1, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 3, true)


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

        root = balancer.remove(root, 5)

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


        assertEquals(6, root?.value)
        assertEquals(1, root?.left?.value)
        assertEquals(4, root?.left?.right?.value)
        assertEquals(7, root?.right?.value)
        assertEquals(3, root?.left?.right?.left?.value)
    }
}