package iterator

import app.model.bst.AVLTree
import app.model.bst.BSTree
import app.model.bst.RBTree
import kotlin.test.Test
import kotlin.test.assertEquals

class InOrderIteratorTest {
    @Test
    fun `test InlOrderIterator to BSTree`() {
        val tree = BSTree<Int>()
        tree.add(8)
        tree.add(4)
        tree.add(10)
        tree.add(3)
        tree.add(1)
        tree.add(5)
        tree.add(9)
        tree.add(7)
        tree.add(6)
        tree.add(2)
        /*                  8
                     /             \
                    4               10
               /        \         /    \
             3           5       9     null
          /    \       /   \
         1    null  null    7
        /  \               /  \
      null  2            6   null
       */
        val iterator = tree.iterator()

        var res = iterator.next()
        assertEquals(res,tree.root?.left?.left?.left?.value) //1

        res = iterator.next()
        assertEquals(res,tree.root?.left?.left?.left?.right?.value) //2

        res = iterator.next()
        assertEquals(res,tree.root?.left?.left?.value) //3

        res = iterator.next()
        assertEquals(res,tree.root?.left?.value) //4

        res = iterator.next()
        assertEquals(res,tree.root?.left?.right?.value) //5

        res = iterator.next()
        assertEquals(res,tree.root?.left?.right?.right?.left?.value) //6

        res = iterator.next()
        assertEquals(res,tree.root?.left?.right?.right?.value) //7

        res = iterator.next()
        assertEquals(res,tree.root?.value) //8

        res = iterator.next()
        assertEquals(res,tree.root?.right?.left?.value) //9

        res = iterator.next()
        assertEquals(res,tree.root?.right?.value) //10
    }

    @Test
    fun `test InlOrderIterator to AVLTree`() {
        val tree = AVLTree<Int>()
        for (i in 0..10) {
            tree.add(i)
        }
        /*            3
                /           \
               1              7
            /     \       /      \
           0       2     5        9
                       /   \     /  \
                      4     6   8    10
         */
        val iterator = tree.iterator()

        var res = iterator.next()
        assertEquals(res,tree.root?.left?.left?.value) //0

        res = iterator.next()
        assertEquals(res,tree.root?.left?.value) //1

        res = iterator.next()
        assertEquals(res,tree.root?.left?.right?.value) //2

        res = iterator.next()
        assertEquals(res,tree.root?.value) //3

        res = iterator.next()
        assertEquals(res,tree.root?.right?.left?.left?.value) //4

        res = iterator.next()
        assertEquals(res,tree.root?.right?.left?.value) //5

        res = iterator.next()
        assertEquals(res,tree.root?.right?.left?.right?.value) //6

        res = iterator.next()
        assertEquals(res,tree.root?.right?.value) //7

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.left?.value) //8

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.value) //9

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.right?.value) //10
    }

    @Test
    fun `test InOrderIterator to RBTree`() {
        val tree = RBTree<Int>()
        for (i in 0..10) {
            tree.add(i)
        }
        /*              3
                 /             \
                1               5
             /     \         /     \
            0       2       4       7
                                  /   \
                                 6     9
                                     /   \
                                    8     10
         */
        val iterator = tree.iterator()

        var res = iterator.next()
        assertEquals(res,tree.root?.left?.left?.value) //0

        res = iterator.next()
        assertEquals(res,tree.root?.left?.value) //1

        res = iterator.next()
        assertEquals(res,tree.root?.left?.right?.value) //2

        res = iterator.next()
        assertEquals(res,tree.root?.value) //3

        res = iterator.next()
        assertEquals(res,tree.root?.right?.left?.value) //4

        res = iterator.next()
        assertEquals(res,tree.root?.right?.value) //5

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.left?.value)//6

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.value) //7

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.right?.left?.value) //8

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.right?.value) //9

        res = iterator.next()
        assertEquals(res,tree.root?.right?.right?.right?.right?.value) //10
    }
}