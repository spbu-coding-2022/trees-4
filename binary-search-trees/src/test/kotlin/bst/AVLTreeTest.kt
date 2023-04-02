import bst.AVLTree
import org.junit.Assert.assertEquals
import org.junit.Test

class AVLTreeTest {
    @Test
    fun `test adding elements to the tree`() {
        val tree = AVLTree<Int>()
        tree.add(5)
        tree.add(3)
        tree.add(7)
        tree.add(2)
        tree.add(4)
        tree.add(6)
        tree.add(8)

        assertEquals(tree.wrappedRoot?.value, 5)
        assertEquals(tree.wrappedRoot?.left?.value, 3)
        assertEquals(tree.wrappedRoot?.right?.value, 7)
        assertEquals(tree.wrappedRoot?.left?.left?.value, 2)
        assertEquals(tree.wrappedRoot?.left?.right?.value, 4)
        assertEquals(tree.wrappedRoot?.right?.left?.value, 6)
        assertEquals(tree.wrappedRoot?.right?.right?.value, 8)
    }

    @Test
    fun `test removing elements from the tree`() {
        val tree = AVLTree<Int>()
        tree.add(5)
        tree.add(3)
        tree.add(7)
        tree.add(2)
        tree.add(4)
        tree.add(6)
        tree.add(8)

        tree.remove(2)
        tree.remove(4)
        tree.remove(6)

        assertEquals(tree.wrappedRoot?.value, 5)
        assertEquals(tree.wrappedRoot?.left?.value, 3)
        assertEquals(tree.wrappedRoot?.right?.value, 7)
        assertEquals(tree.wrappedRoot?.left?.left, null)
        assertEquals(tree.wrappedRoot?.left?.right, null)
        assertEquals(tree.wrappedRoot?.right?.left, null)
        assertEquals(tree.wrappedRoot?.right?.right?.value, 8)
    }
}


