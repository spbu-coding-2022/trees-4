package app.model.bst.balancer

import app.model.bst.node.AVLNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AVLBalancerTest {
    private val balancer = AVLBalancer<Int>()
    @Test
    fun `test method 'add' in avl tree balancer`() {
        val root = AVLNode(5)
        balancer.add(root, 3, true)
        balancer.add(root, 7, true)
        balancer.add(root, 2, true)
        balancer.add(root, 4, true)
        balancer.add(root, 6, true)
        balancer.add(root, 8, true)

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
        assertEquals(6, root.right?.left?.value)
        assertEquals(8, root.right?.right?.value)
    }

    @Test
    fun `test method 'remove' in avl tree balancer`() {
        val root = AVLNode(5)
        balancer.add(root, 3, true)
        balancer.add(root, 7, true)
        balancer.add(root, 2, true)
        balancer.add(root, 4, true)
        balancer.add(root, 6, true)
        balancer.add(root, 8, true)

        /*
            constructed tree:
                    5
                 /      \
               3         7
             /   \      /  \
            2     4    6    8
         */

        balancer.remove(root, 2)

        /*
            constructed tree:
                    5
                 /      \
               3         7
                 \      /  \
                  4    6    8
         */

        assertEquals(5, root.value)
        assertEquals(3, root.left?.value)
        assertNull(root.left?.left)
        assertEquals(4, root.left?.right?.value)
        assertEquals(7, root.right?.value)
        assertEquals(6, root.right?.left?.value)
        assertEquals(8, root.right?.right?.value)
    }
}
