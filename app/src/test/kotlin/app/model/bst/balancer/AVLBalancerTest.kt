package app.model.bst.balancer

import app.model.bst.node.AVLTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AVLBalancerTest {
    private val balancer = AVLBalancer<Int>()
    @Test
    fun `test method 'add' in avl tree balancer`() {
        var root = balancer.add(null, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 8, true)

        /*
             constructed tree:
                    5
                 /      \
               3         7
             /   \      /  \
            2     4    6    8
         */

        assertEquals(5, root.value)
        assertEquals(3, root.left?.value)
        assertEquals(2, root.left?.left?.value)
        assertEquals(4, root.left?.right?.value)
        assertEquals(7, root.right?.value)
        assertEquals(8, root.right?.right?.value)
        assertEquals(6, root.right?.left?.value)
    }

    @Test
    fun `test method 'remove' in avl tree balancer`() {
        var root: AVLTreeNode<Int>? = balancer.add(null, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 8, true)

        /*
            constructed tree:
                    5
                 /      \
               3         7
             /   \      /  \
            2     4    6    8
         */

        root = balancer.remove(root, 5)

        /*
            constructed tree:
                    6
                 /      \
               3         7
             /   \         \
            2     4         8
         */

        assertEquals(6, root?.value)
        assertEquals(3, root?.left?.value)
        assertEquals(2, root?.left?.left?.value)
        assertEquals(4, root?.left?.right?.value)
        assertEquals(7, root?.right?.value)
        assertNull(root?.right?.left?.value)
        assertEquals(8, root?.right?.right?.value)

        root = balancer.remove(root, 3)

        /*
            constructed tree:
                    6
                 /      \
               4         7
             /             \
            2               8
         */

        assertEquals(6, root?.value)
        assertEquals(4, root?.left?.value)
        assertEquals(2, root?.left?.left?.value)
        assertNull(root?.left?.right?.value)
        assertEquals(7, root?.right?.value)
        assertEquals(8, root?.right?.right?.value)
    }
}
