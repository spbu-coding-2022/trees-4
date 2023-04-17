package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.strategy.SerializationStrategy

class Neo4jRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>
) : Repository<E, Node, BST>(strategy) {
    override fun save(tree: BST) {
        TODO("Not yet implemented")
    }

    override fun load(factory: () -> BST): BST {
        TODO("Not yet implemented")
    }

}