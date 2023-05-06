package app.model.bst.balancer

import app.model.bst.node.RedBlackTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals

class RBBalancerTest {
    private val balancer = RBBalancer<Int>()

    @Test
    fun `test method 'add' in rb balancer`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 8, true)
        root = balancer.add(root, 9, true)

        /*
                     constructed tree:
                         4(BLACK)
                       /          \
                 (RED)2           6(RED)
                 /    \          /       \
          (BLACK)1    3(BLACK) (BLACK)5  8(BLACK)
                                         /     \
                                      7(RED)   9(RED)

                 */


        assertEquals(4, root.value)
        assertEquals(2, root.left?.value)
        assertEquals(6, root.right?.value)
        assertEquals(8, root.right?.right?.value)
        assertEquals(5, root.right?.left?.value)
        assertEquals(1, root.left?.left?.value)
        assertEquals(3, root.left?.right?.value)
        assertEquals(7, root.right?.right?.left?.value)
        assertEquals(9, root.right?.right?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.color)
        assertEquals(RedBlackTreeNode.Color.RED, root.left?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root.right?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.right?.right?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.right?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.left?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root.left?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root.right?.right?.left?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root.right?.right?.right?.color)


    }

    @Test
    fun `test method 'remove' in rb balancer, remove root`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)


        /*
                     constructed tree:
                         2(BLACK)
                         /   \
                 (BLACK)1     4(BLACK)
                             /   \
                       (RED)3    5(RED)
                 */


        root = balancer.remove(root, 2)


        /*
                    constructed tree:
                            3(BLACK)
                           /        \
                       1(BLACK)     4(BLACK)
                                      \
                                        5(RED)
                 */


        assertEquals(3, root?.value)
        assertEquals(1, root?.left?.value)
        assertEquals(4, root?.right?.value)
        assertEquals(5, root?.right?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.right?.right?.color)
    }

    @Test
    fun `test method 'remove' in rb balancer, remove black node near the root`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)


        /*
                     constructed tree:
                         2(BLACK)
                         /   \
                 (BLACK)1     4(BLACK)
                             /   \
                       (RED)3    5(RED)
                 */


        root = balancer.remove(root, 1)


        /*
                    constructed tree:
                            4(BLACK)
                           /        \
                       2(BLACK)     5(BLACK)
                           \
                          3(RED)
                 */


        assertEquals(4, root?.value)
        assertEquals(2, root?.left?.value)
        assertEquals(5, root?.right?.value)
        assertEquals(3, root?.left?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.left?.right?.color)
    }

    @Test
    fun `test method 'remove' in rb balancer, remove red node near the root`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)


        /*
                     constructed tree:
                         2(BLACK)
                         /   \
                 (BLACK)1     4(BLACK)
                             /   \
                       (RED)3    5(RED)
         */


        root = balancer.remove(root, 5)


        /*
                     constructed tree:
                         2(BLACK)
                         /   \
                 (BLACK)1     4(BLACK)
                             /
                       (RED)3
         */


        assertEquals(2, root?.value)
        assertEquals(1, root?.left?.value)
        assertEquals(4, root?.right?.value)
        assertEquals(3, root?.right?.left?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.right?.left?.color)
    }

    @Test
    fun `test method 'remove' in rb balancer, remove node, with many descendants`() {
        var root: RedBlackTreeNode<Int>? = balancer.add(null, 1, true)

        root = balancer.add(root, 2, true)
        root = balancer.add(root, 3, true)
        root = balancer.add(root, 4, true)
        root = balancer.add(root, 5, true)
        root = balancer.add(root, 6, true)
        root = balancer.add(root, 7, true)
        root = balancer.add(root, 8, true)
        root = balancer.add(root, 9, true)

        /*
                     constructed tree:
                         4(BLACK)
                       /          \
                 (RED)2           6(RED)
                 /    \          /       \
          (BLACK)1    3(BLACK) (BLACK)5  8(BLACK)
                                         /     \
                                      7(RED)   9(RED)

                 */


        root = balancer.remove(root, 6)


        /*
                     constructed tree:
                         4(BLACK)
                       /          \
                 (RED)2           7(RED)
                 /    \          /       \
          (BLACK)1    3(BLACK) (BLACK)5  8(BLACK)
                                            \
                                            9(RED)
                 */



        assertEquals(4, root?.value)
        assertEquals(2, root?.left?.value)
        assertEquals(7, root?.right?.value)
        assertEquals(8, root?.right?.right?.value)
        assertEquals(5, root?.right?.left?.value)
        assertEquals(1, root?.left?.left?.value)
        assertEquals(3, root?.left?.right?.value)
        assertEquals(9, root?.right?.right?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.right?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.right?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.right?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, root?.left?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, root?.right?.right?.right?.color)
    }

}