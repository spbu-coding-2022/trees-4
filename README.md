# trees

## Основные фичи

1. Поддержка 3 видов (AVL, RB, BS) деревьев поиска с возможностью добавления собственных реализаций
2. (WIP) Поддержка сохранения состояния дерева и восстановаления его из Json, Postgres и Neo4j
3. (WIP) Визуализация различных деревьев поиска

## Деревья поиска

Чтобы создать новый вид дерева нужно сделать 3 действия. Написать балансировщик, возможно, особый тип `BinTreeNode` и
создать класс отнаследованный от `BinarySearchTree`.

```kotlin
class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>>(
        balancer = RBBalancer(),
    )
```

Все наследники `BinarySearchTree` автоматически умеют выполнять поиск, вставку, удаление значений из дерева, а так же
интерироваться по нему различными способами — выполнять inorder, preorder, levelorder обходы

### Сохранение 

Каждое из трёх доступных деревьев можно сохранить одним из следующих способов на выбор: 

 - PostgreSQL
 - Neo4j

Пример Neo4j:
```Kotlin
val st = AVLTreeStrategy({ SerializableValue(it.toString()) }, { it.value.toInt() })
val repo = Neo4jRepo(
    st, Configuration.Builder()
        .uri("bolt://localhost")
        .credentials("neo4j", "password")
        .build()
)
```

Пример PostgreSQL:
```Kotlin
val db = Database.connect(
    "jdbc:postgresql://localhost:5432/", driver = "org.postgresql.Driver",
    user = "postgres", password = "password"
)

val avlRepo = PostgresRepo(
    AVLTreeStrategy({ SerializableValue(it.toString()) }, 
    { it.value.toInt() }),
    db
)
```

Поднять docker можно следующим способом: 

```
docker compose -f "dev-docker-compose.yml" up
```
## Внесение изменений

Внимательно прочитайте раздел [CONTRIBUTING](./CONTRIBUTING.md).

Быстрый способ начать вносить правки:

1. Создате новую ветку (`git checkout -b feat/add-amazing-feature`)
2. Сделайте коммит изменений (`git commit -m "feat: Add some amazing feature"`)
3. Запушьте ветку в origin (`git push origin feat/add-amazing-feature`)
4. Откройте пулл реквест

## Лицензия

Этот проект используeт лицензию MIT. Подробнее в [LICENSE](./LICENSE)