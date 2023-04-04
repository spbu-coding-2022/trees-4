package bst

import utils.InvariantChecker
import kotlin.random.Random
import kotlin.test.*

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AVLTreeTest {
    companion object {
        const val defaultSeed = 42
    }

    private lateinit var tree: AVLTree<Int>
    private lateinit var values: Array<Int>
    private val randomizer = Random(defaultSeed)

    @BeforeTest
    fun init() {
        values = Array(1000) { randomizer.nextInt(10) }
        tree = AVLTree()
    }

    @ParameterizedTest(name = "Are unique elements added: {0}")
    @ValueSource(booleans = [true, false])
    fun `test values addition invariant`(unique: Boolean) {
        for (v in values) {
            tree.add(v, unique)
            assertTrue(InvariantChecker.isBinarySearchTree(tree))
            assertTrue(InvariantChecker.checkNeighborHeights(tree.wrappedRoot))

        }
    }

    @Test
    fun `test all added values are unique`() {
        for (v in values) {
            tree.add(v, unique = true)
        }
        assertEquals(values.sorted().distinct(), tree.iterator().asSequence().toList())
        // Don't use toSet in `actual` part of assertEquals because of false-positive effect
    }

    @ParameterizedTest(name = "Are unique elements added: {0}")
    @ValueSource(booleans = [true, false])
    fun `test values deletion invariant`(unique: Boolean) {
        values.forEach { tree.add(it, unique) }
        values.shuffle()
        for (v in values) {
            tree.remove(v)
            assertTrue(InvariantChecker.isBinarySearchTree(tree))
        }

        assertEquals(null, tree.wrappedRoot) // Tree is empty
    }

    @Test
    fun `test values are being removed gradually`() {
        val uniqueValues = values.toSet()
        var countOfUniqueValues = uniqueValues.size
        values.forEach { tree.add(it, unique = true) }

        for (v in uniqueValues) {
            assertEquals(countOfUniqueValues, tree.iterator().asSequence().toList().size)
            tree.remove(v)
            countOfUniqueValues--
        }

        assertEquals(0, tree.iterator().asSequence().toList().size)
    }

    @Test
    fun `test value heights`() {
        for (v in values) {
            tree.add(v)
        }
    }
}