## 8.1 路径寻路（代码在mypath包下）

​        在使用 GDX-AI 进行路径寻路时，我们通常需要使用 Graph 接口和相关类，如 Node 和 Connection。下面是一个简单的示例，演示如何在 Java 中使用 GDX-AI 来实现路径寻路。

首先，确保你已在项目中添加了 GDX-AI 的依赖库。如果你使用的是 Gradle，你可以在 `build.gradle` 文件中添加以下依赖：

```
implementation ‘com.badlogicgames.gdx:gdx-ai:1.8.2’
```

示例 Java 代码

```
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.IndexedGraph;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.PathSmootherRequest;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath.NodeWithPosition;
import com.badlogic.gdx.ai.pfa.Smoother;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

class Node {
    int index;
    float x, y;

    public Node(int index, float x, float y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }
}

class MapGraph implements IndexedGraph<Node> {
    Array<Node> nodes = new Array<>();
    Array<Connection<Node>> connections = new Array<>();

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return connections; // Simplified for example purposes
    }
}

class LocationHeuristic implements Heuristic<Node> {
    @Override
    public float estimate(Node node, Node endNode) {
        return Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
    }
}

public class PathFindingExample {
    public static void main(String[] args) {
        MapGraph graph = new MapGraph();
        Node startNode = new Node(0, 0, 0);
        Node goalNode = new Node(1, 10, 10);

        graph.nodes.add(startNode);
        graph.nodes.add(goalNode);

        Connection<Node> connection = new Connection<>() {
            @Override
            public float getCost() {
                return 1; // Same cost for simplicity
            }

            @Override
            public Node getFromNode() {
                return startNode;
            }

            @Override
            public Node getToNode() {
                return goalNode;
            }
        };

        graph.connections.add(connection);

        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(graph);
        DefaultGraphPath<Node> path = new DefaultGraphPath<>();
        pathFinder.searchNodePath(startNode, goalNode, new LocationHeuristic(), path);

        for (Node node : path) {
            System.out.println("Node: " + node.index);
        }
    }
}
```

说明

**Node 类**: 表示图中的节点，并包括索引和位置。

**MapGraph 类**: 实现了 `IndexedGraph` 接口，负责管理节点和连接。

**LocationHeuristic 类**: 实现了 `Heuristic` 接口，用于估计从一个节点到另一个节点的距离。

**Main 函数**: 创建一个简单的图并在其中进行路径规划。

运行结果如下：

![image-20240812091746960](./../img/image-20240812091746960.png)

**我来解释一下， pathFinder.searchNodePath(startNode, goalNode, new LocationHeuristic(), path);就是startNode节点到goalNode节点经过的节点**

**我在项目中加了一个更复杂的代码，可以更加展示这种效果**

## 8.2 AI行为树（代码在mybtree包下）

准备工作

确保你已经将 gdx-ai 库添加到你的项目中。如果使用 Maven，可以在 `pom.xml` 中添加如下依赖：

```
 <dependency>
    <groupId>com.badlogicgames.gdx</groupId>
    <artifactId>gdx-ai</artifactId>
    <version>1.8.2</version>
</dependency>
```

示例代码

下面是完整的示例代码：

```
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.Repeat;
import com.badlogic.gdx.ai.btree.leaf.Success;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;

// 定义角色类
class Character {
    public void sayHello() {
        System.out.println(“Hello! I am a character in this game.”);
    }
}

// 自定义任务实现
class PrintHelloTask extends LeafTask<Character> {
    @Override
    public Status execute() {
        Character character = getObject();
        character.sayHello();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Character> copyTo(Task<Character> task) {
        return task;
    }
}

// 主类
public class BehaviorTreeExample {
    public static void main(String[] args) {
        Character character = new Character();
        BehaviorTree<Character> behaviorTree = new BehaviorTree<>(createBehaviorTreeStructure(), character);

        for (int i = 0; i < 10; i++) {
            behaviorTree.step();
        }
    }

    private static Task<Character> createBehaviorTreeStructure() {
        Sequence<Character> rootSequence = new Sequence<>();
        // 使用 ConstantIntegerDistribution 来指定重复次数
        rootSequence.addChild(new Repeat<>(new ConstantIntegerDistribution(1), new PrintHelloTask()));
        rootSequence.addChild(new Success<Character>());
        return rootSequence;
    }
}
```

代码详解

1. 定义角色类

`Character` 类是我们模拟的游戏角色，拥有一个简单的方法 `sayHello`，用于输出打招呼信息。

```
class Character {
    public void sayHello() {
        System.out.println(“Hello! I am a character in this game.”);
    }
}
```



2. 实现自定义任务

`PrintHelloTask` 是一个自定义的任务，继承自 `LeafTask<Character>`。它的主要逻辑是调用角色的 `sayHello` 方法。

```
class PrintHelloTask extends LeafTask<Character> {
    @Override
    public Status execute() {
        Character character = getObject();
        character.sayHello();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Character> copyTo(Task<Character> task) {
        return task;
    }
}
```



3. 主类与行为树创建

在 `BehaviorTreeExample` 中，我们创建了一个角色实例，以及一个行为树实例，随后运行一个简单的循环来执行行为树的逻辑。

```
public class BehaviorTreeExample {
    public static void main(String[] args) {
        Character character = new Character();
        BehaviorTree<Character> behaviorTree = new BehaviorTree<>(createBehaviorTreeStructure(), character);

        for (int i = 0; i < 10; i++) {
            behaviorTree.step();
        }
    }

    private static Task<Character> createBehaviorTreeStructure() {
        Sequence<Character> rootSequence = new Sequence<>();
        // 使用 ConstantIntegerDistribution 来指定重复次数
        rootSequence.addChild(new Repeat<>(new ConstantIntegerDistribution(1), new PrintHelloTask()));
        rootSequence.addChild(new Success<Character>());
        return rootSequence;
    }
}
```



4. 行为树结构

在 `createBehaviorTreeStructure` 方法中，我们定义了行为树的结构。使用 `Sequence` 节点作为根节点，添加一个 `Repeat` 装饰器来控制 `PrintHelloTask` 的执行次数为 1 次。

```
private static Task<Character> createBehaviorTreeStructure() {
    Sequence<Character> rootSequence = new Sequence<>();
    rootSequence.addChild(new Repeat<>(new ConstantIntegerDistribution(1), new PrintHelloTask()));
    rootSequence.addChild(new Success<Character>());
    return rootSequence;
}
```

运行结果：

![image-20240812005850599](./../img/image-20240812005850599.png)



## 8.3 状态机，完整代码（代码在mystatus包下）

LibGDX 的 gdx-ai 库支持有限状态机（Finite State Machine, FSM）来管理对象的状态转换，这在游戏开发中广泛用于管理角色行为状态。下面是一个简单的 Java 示例，展示如何使用 gdx-ai 实现状态机并进行演示。

准备工作

确保你已经将 gdx-ai 库添加到你的项目中。如果使用 Maven，可以在 `pom.xml` 中添加如下依赖：

```
<dependency>
    <groupId>com.badlogicgames.gdx</groupId>
    <artifactId>gdx-ai</artifactId>
    <version>1.8.2</version>
</dependency>
```

示例代码

以下是一个完整的实现状态机的 Java 示例代码：

```
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.msg.Telegram;

class Character {
    private StateMachine<Character, CharacterState> stateMachine;

    public Character() {
        stateMachine = new DefaultStateMachine<>(this, CharacterState.IDLE);
    }

    public void update() {
        stateMachine.update();
    }

    public void changeState(CharacterState newState) {
        stateMachine.changeState(newState);
    }

    public StateMachine<Character, CharacterState> getStateMachine() {
        return stateMachine;
    }

    public void performAction(String action) {
        System.out.println("Performing action: " + action);
    }
}

enum CharacterState implements State<Character> {
    IDLE {
        @Override
        public void update(Character character) {
            System.out.println(“Character is idling…”);
        }
    },
    WALKING {
        @Override
        public void enter(Character character) {
            System.out.println(“Character starts walking.”);
        }

        @Override
        public void update(Character character) {
            character.performAction(“Walking”);
        }

        @Override
        public void exit(Character character) {
            System.out.println(“Character stops walking.”);
        }
    },
    RUNNING {
        @Override
        public void enter(Character character) {
            System.out.println(“Character starts running.”);
        }

        @Override
        public void update(Character character) {
            character.performAction(“Running”);
        }

        @Override
        public void exit(Character character) {
            System.out.println(“Character stops running.”);
        }
    };

    @Override
    public void enter(Character entity) {
    }

    @Override
    public void update(Character entity) {
    }

    @Override
    public void exit(Character entity) {
    }

    @Override
    public boolean onMessage(Character entity, Telegram telegram) {
        return false;
    }
}

public class StateMachineExample {
    public static void main(String[] args) {
        Character character = new Character();

        // 演示状态切换
        character.update(); // Initially idle

        character.changeState(CharacterState.WALKING);
        character.update(); // Walking

        character.changeState(CharacterState.RUNNING);
        character.update(); // Running

        character.changeState(CharacterState.IDLE);
        character.update(); // Back to idle
    }
}
```

 代码解析

\- **Character 类**: 角色类主要管理状态机并调用其更新和状态切换方法。

\- **CharacterState 枚举**: 定义了角色的所有可能状态，包括 `IDLE`、`WALKING` 和 `RUNNING`，每个状态都实现了 `State` 接口，通过 `enter`、`update` 和 `exit` 方法来管理状态特定的行为和打印信息。

\- **StateMachineExample 类**: 主类演示状态机的使用，通过角色实例管理状态并触发变化。

 执行机制

1. **初始化状态**：在 `Character` 的构造函数中初始化状态机，初始状态为 `IDLE`。

2. **状态更新**：在 `update()` 方法中调用当前状态的 `update` 行为。

3. **状态切换**：通过 `changeState()` 方法切换角色的当前状态。

运行结果：

![image-20240812010746563](./../img/image-20240812010746563.png)
