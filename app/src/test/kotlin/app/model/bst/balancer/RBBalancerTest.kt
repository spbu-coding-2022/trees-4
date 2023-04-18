package app.model.bst.balancer

import app.model.bst.node.RedBlackTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals

class RBBalancerTest {
    private val balancer = RBBalancer<Int>()

    @Test
    fun `test method 'add' in rb balancer`() {
        var root = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)


        /*
                     constructed tree:
                           2(BLACK)
                         /   \
                 (BLACK)1     3(BLACK)
                                \
                                 4(RED)
                 */


        assertEquals(2, root.value)
        assertEquals(1, root.left?.value)
        assertEquals(3, root.right?.value)
        assertEquals(4, root.right?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root.right?.right?.color)

    }

    @Test
    fun `test method 'remove' in rb balancer`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        /*
                     constructed tree:
                           2(BLACK)
                         /        \
                    1(BLACK)      3(BLACK)
                                    \
                                   4(RED)
                 */


        root = balancer.remove(root, 2)


        /*
                    constructed tree:
                            3(BLACK)
                           /        \
                       1(BLACK)     4(BLACK)
                 */


        assertEquals(3, root?.value)
        assertEquals(1, root?.left?.value)
        assertEquals(4, root?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.color)
    }

}