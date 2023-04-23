package app.model.bst.balancer

import app.model.bst.node.AVLTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AVLBalancerTest {
    private val balancer = AVLBalancer<Int>()

    @Test
    fun `test left turn when 'add'`() {
        var root = balancer.add(null, 8, true)
        root = balancer.add(root, 7, true)

        /*
             constructed tree:
                    8
                 /
               7
         */

        assertEquals(8,root.value)
        assertEquals(7,root.left?.value)

        root = balancer.add(root, 6, true)

        /*
             constructed tree:
                    7
                 /      \
               6         8
         */

        assertEquals(7,root.value)
        assertEquals(6,root.left?.value)
        assertEquals(8,root.right?.value)

        root = balancer.add(root, 5, true)
        root = balancer.add(root, 4, true)

        /*
            constructed tree:
                   7
                /      \
               5        8
            /    \
           4      6
        */

        assertEquals(7,root.value)
        assertEquals(5,root.left?.value)
        assertEquals(8,root.right?.value)
        assertEquals(4,root.left?.left?.value)
        assertEquals(6,root.left?.right?.value)

        root = balancer.add(root, 3, true)

        /*
            constructed tree:
                   5
                /      \
               4        7
              /       /   \
             3       6     8
        */

        assertEquals(5,root.value)
        assertEquals(4,root.left?.value)
        assertEquals(7,root.right?.value)
        assertEquals(3,root.left?.left?.value)
        assertEquals(6,root.right?.left?.value)
        assertEquals(8,root.right?.right?.value)

        root = balancer.add(root, 2, true)

        /*
            constructed tree:
                   5
                /      \
               3        7
             /  \     /   \
            2    4   6     8
        */

        assertEquals(3,root.left?.value)
        assertEquals(2,root.left?.left?.value)
        assertEquals(4,root.left?.right?.value)

        root = balancer.add(root, 1, true)
        root = balancer.add(root, 0, true)

        /*
            constructed tree:
                   5
                /      \
               3        7
             /  \     /   \
            1    4   6     8
          /   \
         0     2
        */

        assertEquals(1,root.left?.left?.value)
        assertEquals(0,root.left?.left?.left?.value)
        assertEquals(2,root.left?.left?.right?.value)
    }

    @Test
    fun `test right turn when 'add'`() {
        var root = balancer.add(null, 2, true)
        root = balancer.add(root, 3, true)

        /*
             constructed tree:
                    2
                      \
                       3
         */

        assertEquals(2,root.value)
        assertEquals(3,root.right?.value)

        root = balancer.add(root, 4, true)

        /*
             constructed tree:
                    3
                  /   \
                 2     4
         */

        assertEquals(3,root.value)
        assertEquals(2,root.left?.value)
        assertEquals(4,root.right?.value)

        root = balancer.add(root, 5, true)
        root = balancer.add(root, 6, true)

        /*
             constructed tree:
                    3
                  /   \
                 2     5
                     /   \
                    4     6
         */

        assertEquals(5,root.right?.value)
        assertEquals(4,root.right?.left?.value)
        assertEquals(6,root.right?.right?.value)

        root = balancer.add(root, 7, true)

        /*
             constructed tree:
                    5
                 /      \
               3         6
             /   \         \
            2     4         7
         */

        assertEquals(5, root.value)
        assertEquals(3, root.left?.value)
        assertEquals(2, root.left?.left?.value)
        assertEquals(4, root.left?.right?.value)
        assertEquals(6, root.right?.value)
        assertEquals(7, root.right?.right?.value)

        root = balancer.add(root, 8, true)

        /*
             constructed tree:
                    5
                 /      \
               3         7
             /   \      /  \
            2     4    6    8
         */

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
