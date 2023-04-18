package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.*
import app.model.repo.serialization.strategy.SerializationStrategy
import org.neo4j.ogm.annotation.*
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.cypher.Filters
import org.neo4j.ogm.session.SessionFactory

@NodeEntity
class SerializableNodeEntity(
    @Property
    var value: SerializableValue,

    @Property
    var metadata: Metadata,

    @Relationship(type = "LEFT")
    var left: SerializableNodeEntity? = null,

    @Relationship(type = "RIGHT")
    var right: SerializableNodeEntity? = null,
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}

@NodeEntity
class SerializableTreeEntity(
    @Property
    var verboseName: String,

    @Property
    var typeOfTree: TypeOfTree,

    @Relationship(type = "ROOT")
    var root: SerializableNodeEntity? = null,
) {

    @Id
    @GeneratedValue
    var id: Long? = null

}

/**
 * A Neo4j repository for serializable tree data structures.
 *
 * @param <E> the type of elements stored in the tree
 * @param <Node> the type of nodes in the tree
 * @param <BST> the type of binary search tree being serialized or deserialized
 * @property strategy the serialization strategy used by the repository
 * @property configuration the Neo4j configuration used by the repository
 */
class Neo4jRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>,
    configuration: Configuration
) : Repository<E, Node, BST>(strategy) {

    private val sessionFactory = SessionFactory(configuration, "app.model.repo")
    private val session = sessionFactory.openSession()

    /**
     * Converts a serializable node entity to a serializable node.
     *
     * @receiver the serializable node entity to convert
     * @return the serializable node that was converted from the Neo4j ogm entity
     */
    private fun SerializableNodeEntity.toNode(): SerializableNode {
        return SerializableNode(
            value,
            metadata,
            left?.toNode(),
            right?.toNode()
        )
    }

    /**
     * Converts a serializable tree entity to a serializable tree.
     *
     * @receiver the serializable tree entity to convert
     * @return the serializable tree that was converted from the Neo4j ogm entity
     */
    private fun SerializableTreeEntity.toTree(): SerializableTree {
        return SerializableTree(
            verboseName,
            typeOfTree,
            root?.toNode()
        )
    }

    /**
     * Converts a serializable node to a serializable node entity.
     *
     * @receiver the serializable node to convert
     * @return the serializable node entity that was converted from the node
     */
    private fun SerializableNode.toEntity(): SerializableNodeEntity {
        return SerializableNodeEntity(
            value,
            metadata,
            left?.toEntity(),
            right?.toEntity()
        )
    }

    /**
     * Converts a serializable tree to a serializable tree entity.
     *
     * @receiver the serializable tree to convert
     * @return the serializable tree entity that was converted from the tree
     */
    private fun SerializableTree.toEntity(): SerializableTreeEntity {
        return SerializableTreeEntity(
            verboseName,
            typeOfTree,
            root?.toEntity()
        )
    }

    /**
     * Saves a binary search tree to the Neo4j database.
     *
     * @param verboseName the name of the tree being saved
     * @param tree the binary search tree to save
     */
    override fun save(verboseName: String, tree: BST) {
        deleteByVerboseName(verboseName)
        val entityTree = tree.toSerializableTree(verboseName).toEntity()
        session.save(entityTree)
    }

    /**
     * Finds a serializable tree entity by its verbose name.
     *
     * @param verboseName the verbose name of the tree entity to find
     * @return a list of serializable tree entities matching the verbose name and type of tree
     */
    private fun findByVerboseName(verboseName: String) = session.loadAll(
        SerializableTreeEntity::class.java,
        Filters().and(
            Filter("verboseName", ComparisonOperator.EQUALS, verboseName)
        ).and(
            Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree)
        ),
        -1
    )

    /**
     * Loads a binary search tree from the Neo4j database by its verbose name.
     *
     * @param verboseName the verbose name of the tree to load
     * @param factory a function that creates a new instance of the binary search tree being loaded
     * @return the binary search tree loaded from the database
     */
    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST {
        val treeEntity = findByVerboseName(verboseName).singleOrNull()

        return factory().apply {
            root = strategy.buildNode(treeEntity?.toTree()?.root)
        }
    }

    /**
     * Deletes a serializable tree entity and all of its related nodes and relationships from the Neo4j database.
     *
     * @param verboseName the verbose name of the tree entity to delete
     */
    override fun deleteByVerboseName(verboseName: String) {
        session.query(
            "MATCH toDelete=(t:SerializableTreeEntity {typeOfTree: \$typeOfTree, verboseName : \$verboseName})-[*0..]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "verboseName" to verboseName)
        )
    }
}