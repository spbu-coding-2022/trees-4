package app.model.bst.balancer

import app.model.bst.RBTree
import app.model.bst.node.RedBlackTreeNode
import kotlin.test.Test
import kotlin.test.assertEquals

class RBBalancerTest {


    @Test
    fun `test method 'add' in rb tree`() {
        val tree = RBTree<Int>()

        tree.add(1)
        tree.add(2 )
        tree.add(3 )
        tree.add(4 )


        /*
                     constructed tree:
                           2(BLACK)
                         /   \
                 (BLACK)1     3(BLACK)
                                \
                                 4(RED)
                 */


        assertEquals(2, tree.root?.value)
        assertEquals(1, tree.root?.left?.value)
        assertEquals(3, tree.root?.right?.value)
        assertEquals(4, tree.root?.right?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.right?.color)
        assertEquals(RedBlackTreeNode.Color.RED, tree.root?.right?.right?.color)

    }

    @Test
    fun `test method 'remove' in rb tree`() {
        val tree = RBTree<Int>()

        tree.add(1)
        tree.add(2)
        tree.add(3)
        tree.add(4)


        /*
                     constructed tree:
                           2(BLACK)
                         /   \
                 (BLACK)1     3(BLACK)
                                \
                                 4(RED)
                 */


        tree.remove( 2)


        /*
                    constructed tree:
                            3(BLACK)
                           /        \
                       1(BLACK)     4(BLACK)
                 */


        assertEquals(3, tree.root?.value)
        assertEquals(1, tree.root?.left?.value)
        assertEquals(4, tree.root?.right?.value)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.left?.color)
        assertEquals(RedBlackTreeNode.Color.BLACK, tree.root?.right?.color)
    }

}