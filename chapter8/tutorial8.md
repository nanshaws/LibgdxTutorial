## 8.1 路径查找

​        在Libgdx中，您可以使用FileHandle类来查找和管理文件和目录。下面是一个如何使用FileHandle类在Libgdx项目的assets目录中查找文件的示例:

```
public class Game extends ApplicationAdapter {
    @Override
    public void create() {
        FileHandle file = Gdx.files.internal("data/example.txt");
        if (file.exists()) {
            String content = file.readString();
            System.out.println("File content: " + content);
        } else {
            System.out.println("File not found");
        }
    }
}
```

​        在这个例子中，Gdx.files.internal方法用于获取资产文件夹“data”目录下名为“example.txt”的文件的FileHandle。然后调用exists方法来检查文件是否存在，并调用readString方法来读取文件的内容。

如果需要查找目录，可以使用list方法获得目录中文件和目录的列表。这里有一个例子:

```
public class Game extends ApplicationAdapter {
    @Override
    public void create() {
        FileHandle dir = Gdx.files.internal("data");

        if (dir.isDirectory()) {
            FileHandle[] files = dir.list();

            for (FileHandle file : files) {
                System.out.println("File: " + file.name());
            }
        } else {
            System.out.println("Not a directory");
        }
    }
}
```

在本例中，使用Gdx.files.internal方法获取“data”目录的FileHandle。然后调用isDirectory方法来检查该目录是否存在，并调用list方法来获取该目录中的文件和目录的列表。调用name方法来获取每个文件的名称。

​        需要注意的是，FileHandle类提供了几种管理文件和目录的方法，包括复制、移动、删除和mkdirs。您可以使用这些方法执行各种文件操作，例如复制文件、移动文件、删除文件或创建目录。

下面是一个如何使用FileHandle类复制文件的例子:

```
public class Game extends ApplicationAdapter {
    @Override
    public void create() {
        FileHandle srcFile = Gdx.files.internal("data/example.txt");
        FileHandle destFile = Gdx.files.internal("data/example_copy.txt");

        if (srcFile.exists()) {
            srcFile.copyTo(destFile);
            System.out.println("File copied");
        } else {
            System.out.println("Source file not found");
        }
    }
}
```

​        在本例中，FileHandle类用于获取源文件和目标文件的FileHandle。调用exists方法检查源文件是否存在，调用copyTo方法将源文件复制到目标文件。println方法用于打印一条消息，表明文件是否复制成功。



在libGDX中，可以使用AStar类进行路径查找。以下是一个简单的示例：

```
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.PathFinderAdapter;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder.Path;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PathfindingExample {
    public static void main(String[] args) {
        // 加载地图
        TiledMap map = new TmxMapLoader().load("pathfinding_map.tmx");
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        // 创建路径查找器
        PathFinderAdapter<Vector2> pathFinderAdapter = new TiledMapAdapter(layer);
        IndexedAStarPathFinder<Vector2> pathFinder = new IndexedAStarPathFinder<>(pathFinderAdapter, false);

        // 设置起点和终点
        Vector2 start = new Vector2(1, 1);
        Vector2 end = new Vector2(10, 10);

        // 查找路径
        Path<Vector2> path = pathFinder.findPath(start, end);
        System.out.println("找到的路径：" + path);
    }
}   
```

## 8.2 AI行为树

在libGDX中，可以使用BehaviorTree类进行AI行为树的构建和执行。以下是一个简单的示例：

```
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.decorator.Invert;
import com.badlogic.gdx.ai.btree.decorator.Succeeder;
import com.badlogic.gdx.ai.btree.task.Condition;
import com.badlogic.gdx.ai.btree.task.Selector;
import com.badlogic.gdx.ai.btree.task.Sequence;

public class BehaviorTreeExample {
    public static void main(String[] args) {
        // 创建行为树节点
        Condition isHealthy = new IsHealthy();
        Condition isHungry = new IsHungry();
        Task eat = new Eat();
        Task drink = new Drink();

        // 构建行为树
        Sequence sequence = new Sequence();
        sequence.addChild(isHealthy);
        sequence.addChild(isHungry);

        Decorator invert = new Invert();
        invert.addChild(isHealthy);

        Selector selector = new Selector();
        selector.addChild(sequence);
        selector.addChild(invert);
        selector.addChild(eat);
        selector.addChild(drink);

        BehaviorTree<Void> behaviorTree = new BehaviorTree<>(selector);

        // 执行行为树
        while (!behaviorTree.isFinished()) {
            behaviorTree.step();
        }
    }
}    
```

## 8.3 状态机，完整代码

在libGDX中，可以使用StateMachine类进行状态机的构建和执行。以下是一个简单的示例：

```
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.fsm.StateTransition;
import com.badlogic.gdx.ai.fsm.Transition;

public class StateMachineExample {
    public static void main(String[] args) {
        // 创建状态
        State idleState = new IdleState();
        State walkState = new WalkState();
        State runState = new RunState();

        // 创建状态转换条件
        Transition idleToWalk = new Transition() {
            @Override
            public boolean evaluate() {
                return playerIsWalking();
            }
        };
        Transition walkToRun = new Transition() {
            @Override
            public boolean evaluate() {
                return playerIsRunning();
            }
        };
        Transition runToIdle = new Transition() {
            @Override
            public boolean evaluate() {
                return playerIsIdle();
            }
        };

        // 创建状态转换
        StateTransition idleToWalkTransition = new StateTransition(idleState, walkState, idleToWalk);
        StateTransition walkToRunTransition = new StateTransition(walkState, runState, walkToRun);
        StateTransition runToIdleTransition = new StateTransition(runState, idleState, runToIdle);

        // 创建状态机并添加状态转换
        StateMachine stateMachine = new StateMachine();
        stateMachine.addTransition(idleToWalkTransition);
        stateMachine.addTransition(walkToRunTransition);
        stateMachine.addTransition(runToIdleTransition);

        // 设置初始状态并执行状态机
        stateMachine.setInitialState(idleState);
        while (!stateMachine.isInState(idleState)) {
            stateMachine.update();
        }
    }
}

```

