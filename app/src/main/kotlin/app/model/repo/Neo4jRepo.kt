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

class Neo4jRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>
) : Repository<E, Node, BST>(strategy) {
    private val configuration = Configuration.Builder()
        .uri("bolt://localhost")
        .credentials("neo4j", "password") //TODO: use settings for this parameters
        .build()
    private val sessionFactory = SessionFactory(configuration, "app.model.repo")
    private val session = sessionFactory.openSession()

    private fun SerializableNodeEntity.toNode(): SerializableNode {
        return SerializableNode(
            value,
            metadata,
            left?.toNode(),
            right?.toNode()
        )
    }

    private fun SerializableTreeEntity.toTree(): SerializableTree {
        return SerializableTree(
            verboseName,
            typeOfTree,
            root?.toNode()
        )
    }


    private fun SerializableNode.toEntity(): SerializableNodeEntity {
        return SerializableNodeEntity(
            value,
            metadata,
            left?.toEntity(),
            right?.toEntity()
        )
    }

    private fun SerializableTree.toEntity(): SerializableTreeEntity {
        return SerializableTreeEntity(
            verboseName,
            typeOfTree,
            root?.toEntity()
        )
    }

    override fun save(verboseName: String, tree: BST) {
        deleteByVerboseName(verboseName)
        val entityTree = tree.toSerializableTree(verboseName).toEntity()
        session.save(entityTree)
    }

    private fun findByVerboseName(verboseName: String) = session.loadAll(
        SerializableTreeEntity::class.java,
        Filters().and(
            Filter("verboseName", ComparisonOperator.EQUALS, verboseName)
        ).and(
            Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree)
        ),
        -1
    )

    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST {
        val treeEntity = findByVerboseName(verboseName).singleOrNull()

        return factory().apply {
            root = strategy.buildNode(treeEntity?.toTree()?.root)
        }
    }

    override fun deleteByVerboseName(verboseName: String) {
        session.query(
            "MATCH toDelete=(t:SerializableTreeEntity {typeOfTree: \$typeOfTree, verboseName : \$verboseName})-[*]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "verboseName" to verboseName)
        )
    }
}