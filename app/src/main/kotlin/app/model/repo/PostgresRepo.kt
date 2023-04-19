package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableTree
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.strategy.SerializationStrategy
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

private object NodesTable : IntIdTable("nodes") {
    val value = text("data")
    val metadata = text("metadata")
    val left = reference("left_id", NodesTable).nullable()
    val right = reference("right_id", NodesTable).nullable()
    val tree = reference("tree_id", TreesTable, onDelete = ReferenceOption.CASCADE)
}

private object TreesTable : IntIdTable("trees") {
    val verboseName = text("name")
    val typeOfTree = text("type")
    val root = reference("root_node_id", NodesTable).nullable()

    init {
        uniqueIndex(verboseName, typeOfTree)
    }
}

class DatabaseNodeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DatabaseNodeEntity>(NodesTable)

    var value by NodesTable.value
    var metadata by NodesTable.metadata
    var left by DatabaseNodeEntity optionalReferencedOn NodesTable.left
    var right by DatabaseNodeEntity optionalReferencedOn NodesTable.right
    var tree by DatabaseTreeEntity referencedOn NodesTable.tree
}

class DatabaseTreeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DatabaseTreeEntity>(TreesTable)

    var verboseName by TreesTable.verboseName
    var typeOfTree by TreesTable.typeOfTree
    var root by DatabaseNodeEntity optionalReferencedOn TreesTable.root
}


class PostgresRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>,
    private val db: Database
) : Repository<E, Node, BST>(strategy) {
    init {
        transaction(db) {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    private fun SerializableNode.toEntity(tree: DatabaseTreeEntity): DatabaseNodeEntity {
        return DatabaseNodeEntity.new {
            this@new.value = this@toEntity.value.value
            this@new.metadata = this@toEntity.metadata.value
            this@new.left = this@toEntity.left?.toEntity(tree)
            this@new.right = this@toEntity.right?.toEntity(tree)
            this@new.tree = tree
        }
    }

    private fun SerializableTree.toEntity(): DatabaseTreeEntity {
        return DatabaseTreeEntity.new {
            this@new.verboseName = this@toEntity.verboseName
            this@new.typeOfTree = strategy.typeOfTree.toString()
        }.also { it.root = root?.toEntity(it) }
    }

    private fun DatabaseTreeEntity.toTree(): SerializableTree {
        return SerializableTree(
            verboseName = verboseName,
            typeOfTree = strategy.typeOfTree,
            root = root?.toNode(),
        )
    }

    private fun DatabaseNodeEntity.toNode(): SerializableNode {
        return SerializableNode(
            SerializableValue(value),
            Metadata(metadata),
            left?.toNode(),
            right?.toNode()
        )
    }


    override fun save(verboseName: String, tree: BST): Unit = transaction(db) {
        deleteByVerboseName(verboseName)
        tree.toSerializableTree(verboseName).toEntity()
    }


    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST =
        transaction(db) {
            val entity = DatabaseTreeEntity.find(
                TreesTable.typeOfTree eq strategy.typeOfTree.toString() and (TreesTable.verboseName eq verboseName)
            ).firstOrNull()

            factory().apply { root = strategy.buildNode(entity?.root?.toNode()) }
        }

    override fun deleteByVerboseName(verboseName: String): Unit = transaction(db) {
        DatabaseTreeEntity.find(
            TreesTable.typeOfTree eq strategy.typeOfTree.toString() and (TreesTable.verboseName eq verboseName)
        ).firstOrNull()?.delete()
    }
}