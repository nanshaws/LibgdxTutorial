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









## 8.6 消息处理（代码在myMessage包下）

消息处理在游戏中是非常重要的，比如说一个玩家攻击npc，这个时候就要传送一个attack的信息从玩家给到npc，同时npc拿到这个信息会进行逃跑或者反击处理，消息传递的过程中可以给其附加消息，就比如说当玩家带枪去攻击npc的时候，npc就只能逃跑

1. 消息定义

首先，你需要定义消息的类型。在游戏中，消息通常用枚举类型来表示。

```
public enum MessageType {
    ATTACK,
    DEFEND,
    PATROL
}
```

2. 消息监听器和派发器

接下来，设置消息监听器（一个可以接收消息的组件）和消息派发器（负责发送消息的对象）。

```
import com.badlogic.gdx.ai.msg.*;

public class AIEntity implements Telegraph {
    private String name;

    public AIEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        MessageType messageType = MessageType.values()[msg.message];
        switch (messageType) {
            case ATTACK:
                System.out.println(name + " received a ATTACK message with extra info: " + msg.extraInfo);
                break;
            case DEFEND:
                System.out.println(name + " received a DEFEND message with extra info: " + msg.extraInfo);
                break;
            case PATROL:
                System.out.println(name + " received a PATROL message with extra info: " + msg.extraInfo);
                break;
            default:
                return false;
        }
        return true;
    }
}

public class AIDemo {
    public static void main(String[] args) {
        // 创建实体和消息派发器
        AIEntity entity1 = new AIEntity(“Entity1”);
        AIEntity entity2 = new AIEntity(“Entity2”);

        MessageDispatcher dispatcher = new MessageDispatcher();

        // 注册实体到消息派发器
        dispatcher.addListener(entity1, MessageType.ATTACK.ordinal());
        dispatcher.addListener(entity2, MessageType.DEFEND.ordinal());
        dispatcher.addListener(entity2, MessageType.PATROL.ordinal());

        // 发送消息
        dispatcher.dispatchMessage(MessageType.ATTACK.ordinal());
        dispatcher.dispatchMessage(entity1, entity2, MessageType.PATROL.ordinal(), "ExtraInfo", false);
    }
}
```



 代码说明

1. **Telegraph 接口**: `AIEntity` 实现了 `Telegraph` 接口，使其能够接收消息。`handleMessage` 方法是处理收到消息的地方。

2. **MessageDispatcher**: `MessageDispatcher` 是负责消息发送的组件。它可以直接发送消息给指定的接收者，或广播消息给所有注册了监听器的实体。

3. **消息发送和接收**:
   \- 注册监听器：使用 `addListener` 方法来规定哪些实体对哪些消息感兴趣，此处通过 `MessageType` 的枚举索引来注册监听器。
   \- 发送消息：通过 `dispatchMessage` 方法发送消息。消息可以包括一个额外的信息体（如 `"ExtraInfo"`），以便接收者利用。

4. **handleMessage**: 接收到消息后，根据消息类型进行相应的操作。此处通过日志输出来模拟游戏中不同的响应行为。

 参数解析

 dispatcher.dispatchMessage(entity1, entity2, MessageType.PATROL.ordinal(), "ExtraInfo", false);

1. **`entity1`** (`Telegraph sender`):
   \- 这是消息的发送者。它是一个实现了 `Telegraph` 接口的对象。在游戏中，这通常是某个意识到需要触发事件或进行通信的角色或系统。

2. **`entity2`** (`Telegraph receiver`):
   \- 这是消息的接收者。它也是一个实现了 `Telegraph` 接口的对象。这个对象会被通知有消息到达并负责处理这个消息。如果接收者为 `null`，则消息会被广播给所有注册了该消息类型的监听器。

3. **`MessageType.PATROL.ordinal()`** (`int msg`):
   \- 这是消息的类型。`MessageType` 是一个枚举，`PATROL` 是其中的一个枚举常量。使用 `ordinal()` 方法将枚举常量转化为其枚举定义中的整数顺序（通常从 0 开始）。

4. **`"ExtraInfo"`** (`Object extraInfo`):
   \- 这是附加信息，随同消息一起发出。可以是任何对象，用于传递额外的数据或上下文，以便接收者理解消息的意图或具体内容。

5. **`false`** (`boolean needsReturnReceipt`):
   \- 这个参数表示是否需要消息回执（return receipt）。`false` 意味着不要求发送消息的确认回执，大多数情况下用于简单的单向通知。

运行结果如下：

![image-20240812102423803](./../img/image-20240812102423803.png)
