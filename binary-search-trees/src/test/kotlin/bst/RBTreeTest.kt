package bst

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import utils.InvariantChecker
import kotlin.random.Random
import kotlin.test.*

class RBTreeTest {
    companion object {
        const val defaultSeed = 42
    }

    private lateinit var tree: RBTree<Int>
    private lateinit var values: Array<Int>
    private val randomizer = Random(defaultSeed)

    @BeforeTest
    fun init() {
        values = Array(1000) { randomizer.nextInt(10) }
        tree = RBTree()
    }

    @ParameterizedTest(name = "Are unique elements added: {0}")
    @ValueSource(booleans = [true, false])
    fun `test values addition invariant`(unique: Boolean) {
        for (v in values) {
            tree.add(v, unique)
            assertTrue(InvariantChecker.isBinarySearchTree(tree))
            assertTrue(InvariantChecker.isColoredRight(tree))
            assertTrue(InvariantChecker.isBlackHeightBalanced(tree))
        }
    }

    @Test
    fun `test all added values are unique`() {
        for (v in values) {
            tree.add(v, unique = true)
        }
        Assertions.assertEquals(values.sorted().distinct(), tree.iterator().asSequence().toList())
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
            assertTrue(InvariantChecker.isBlackHeightBalanced(tree))
        }

        Assertions.assertEquals(null, tree.wrappedRoot) // Tree is empty
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

        Assertions.assertEquals(0, tree.iterator().asSequence().toList().size)
    }
}