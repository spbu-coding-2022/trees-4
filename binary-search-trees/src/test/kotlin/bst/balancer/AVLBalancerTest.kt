package bst.balancer

import bst.node.AVLNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AVLBalancerTest {
    private val balancer = AVLBalancer<Int>()
    @Test
    fun `test add`() {
        val root = AVLNode(5)
        balancer.add(root, 3, true)
        balancer.add(root, 7, true)
        balancer.add(root, 2, true)
        balancer.add(root, 4, true)
        balancer.add(root, 6, true)
        balancer.add(root, 8, true)
        assertEquals(5, root.value)
        assertEquals(3, root.left?.value)
        assertEquals(2, root.left?.left?.value)
        assertEquals(4, root.left?.right?.value)
        assertEquals(7, root.right?.value)
        assertEquals(6, root.right?.left?.value)
        assertEquals(8, root.right?.right?.value)
    }

    @Test
    fun `test remove`() {
        val root = AVLNode(5)
        balancer.add(root, 3, true)
        balancer.add(root, 7, true)
        balancer.add(root, 2, true)
        balancer.add(root, 4, true)
        balancer.add(root, 6, true)
        balancer.add(root, 8, true)
        balancer.remove(root, 2)
        assertEquals(5, root.value)
        assertEquals(3, root.left?.value)
        assertNull(root.left?.left)
        assertEquals(4, root.left?.right?.value)
        assertEquals(7, root.right?.value)
        assertEquals(6, root.right?.left?.value)
        assertEquals(8, root.right?.right?.value)
    }
}
