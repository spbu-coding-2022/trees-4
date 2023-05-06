# trees

Наше приложение рисует и управляет тремя типами бинарных деревьев: красно-черное, AVL и обычное дерево поиска.
Оно позволяет добавлять и удалять элементы из деревьев, а также сохранять деревья, над которыми велась работа, что 
обеспечивает сохранение данных между сеансами работы приложения.


## Графический интерфейс

   <img src=app/src/main/resources/view.png align="center">

1. При запуске приложения вы увидите на экране три типа дерева. Нажмите на одно из них, чтобы начать работу с нужным деревом.
   
   <img src=app/src/main/resources/tree.png align="center">

2. Для добавления в дерево новой ноды напишите нужное значение в соответсвующем окне и нажмите на кнопку +.

   <img src=app/src/main/resources/add.png align="center">

3. Для удаления ноды из дерева, напишите ее значение в соответсвующем окне и нажмите на кнопку -.

   <img src=app/src/main/resources/remove.png align="center">

4. Для сохранения дерева, нажмите на кнопку "Save tree to...". В появившемся диалоговом окне выберите место для сохранения файла и введите название файла.
 
   <img src=app/src/main/resources/saveTree.png align="center">

5. Для загрузки дерева из файла, нажмите на кнопку "Load tree from...". В появившемся диалоговом окне выберите нужный тип дерева и нужный файл с этим типом дерева.
   (при несоответствии типа дерева, указанного в диалоговом окне, с тем, который в файле, ничего не произойдет)
   
   <img src=app/src/main/resources/loadTree.png align="center">

6. Для поиска ноды в дереве, введите ее значение в соответствующем поле и нажмите на лупу. Если нода найдена, будет выведено поддерево, для которого данная нода станет рутом. Если нода не найдена, ничего не произойдет.
   (Чтобы вернуть изначальное дерево нажмите на кнопку "Reset tree")
   
   <img src=app/src/main/resources/search.png align="center">

7. Если вам нужно вернуть изначальное положение экрана, нажмите на кнопку "Reset view".

   <img src=app/src/main/resources/resetView.png align="center">

## Основные фичи

1. Поддержка 3 видов (AVL, RB, BS) деревьев поиска с возможностью добавления собственных реализаций
2. (WIP) Поддержка сохранения состояния дерева и восстановления его из Json, Postgres и Neo4j
3. (WIP) Визуализация различных деревьев поиска

## Информация про архитектуру проекта 

### Деревья поиска

Чтобы создать новый вид дерева нужно сделать 3 действия. Написать балансировщик, возможно, особый тип `BinTreeNode` и
создать класс отнаследованный от `BinarySearchTree`.

```kotlin
class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>>(
        balancer = RBBalancer(),
    )
```

Все наследники `BinarySearchTree` автоматически умеют выполнять поиск, вставку, удаление значений из дерева, а так же
итерироваться по нему различными способами — выполнять inorder, preorder, levelorder обходы.

### Использование

Создать дерево и использовать доступные для него методы можно следующим образом:

```kotlin
val tree = RBTree<Int>()

tree.add(2)
tree.add(5)
tree.add(1)

tree.remove(2)

1 in tree // true
```

search возвращает Boolean  


Тип создаваемого дерева указывается одним из следующих способов: RBTree<Тип>(), AVLTree<>(), BSTree<>().

Для RB, AVL, или BS дерева соответственно. 


### Cохранение 

Каждое из трёх доступных деревьев можно сохранить одним из следующих способов на выбор: 

 - Json 
 - PostgreSQL
 - Neo4j

Пример Json:
```Kotlin
val strategy = AVLTreeStrategy({ SerializableValue(it.toString()) }, { it.value.toInt() })
val repo = JsonRepo(strategy)
    
val tree = AVLTree<Int>()
    
repo.save("myTree", tree)
    
repo.loadByVerboseName("myTree",::AVLTree)
    
repo.deleteByVerboseName("myTree")
```

Пример Neo4j:
```Kotlin
val strategy = RBTreeStrategy({ SerializableValue(it.toString()) }, { it.value.toInt() })
val repo = Neo4jRepo(
    strategy, Configuration.Builder()
        .uri("bolt://localhost")
        .credentials("neo4j", "password")
        .build()
)

val tree = RBTree<Int>()

repo.save("myTree", tree) 

repo.loadByVerboseName("myTree", ::RBTree)

repo.deleteByVerboseNam("myTree")
```

Пример PostgreSQL:
```Kotlin
val database = Database.connect(
    "jdbc:postgresql://localhost:5432/", driver = "org.postgresql.Driver",
    user = "postgres", password = "password"
)

val repo = PostgresRepo(
    AVLTreeStrategy({ SerializableValue(it.toString()) }, 
    { it.value.toInt() }),
    database
)

val tree = AVLTree<Int>()

repo.save("myTree", tree)

repo.loadByVerboseName("myTree", ::AVLTree)

repo.deleteByVerboseNam("myTree")
```

Для корректной работы базы данных приложения, а также правильного отображения деревьев необходимо поднять docker.

Чтобы это сделать нужно обратиться к [документации](https://docs.docker.com/desktop/).

Также это сделать можно следующим способом:

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

Этот проект используeт лицензию MIT. Подробнее в [LICENSE](./LICENSE).
