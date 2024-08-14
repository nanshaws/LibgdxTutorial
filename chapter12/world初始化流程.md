在Artemis ECS（Entity Component System）框架中，`World` 类是核心管理类，负责管理实体（Entity）、组件（Component）和系统（System）。`World` 类的初始化流程主要由其构造方法和相关配置（如`WorldConfiguration`）驱动。以下是`World`类初始化流程的详细介绍：

### 1. **构造方法 `World(WorldConfiguration configuration)`**:
`World`的构造方法接收一个`WorldConfiguration`对象，用于配置`World`的初始化。这个构造方法主要完成以下步骤：

- **`partition` 和 `systemsBag` 初始化**:
    - `partition` 是 `WorldSegment` 的一个实例，持有`injector`和`systems`(系统的class->系统实例映射)。
    - `systemsBag` 是 `WorldConfiguration` 中系统的集合。

- **`ComponentManager`、`EntityManager`、`AspectSubscriptionManager` 初始化**:
    - `ComponentManager` (`cm`)、`EntityManager` (`em`) 和 `AspectSubscriptionManager` (`asm`) 是`World`的核心管理器，用于管理组件、实体和系统之间的关系。如果这些管理器在配置中已经被初始化(默认被null占据位置，未提供修改方法)，则直接使用；否则创建新的实例。

- **`BatchChangeProcessor` 和 `alwaysDelayComponentRemoval` 初始化**:
    - `batchProcessor` 是一个批量处理组件变化的工具。
    - `alwaysDelayComponentRemoval` 表示是否总是延迟移除组件，这个配置值来自于`WorldConfiguration`。

- **调用`WorldConfiguration`的 `initialize` 方法**:
    - 该方法将`World`实例本身传入，完成更复杂的初始化逻辑（核心）。

### 2. **`WorldConfiguration` 的初始化 (`initialize`)**:
该方法在`World`的构造方法中被调用，负责初始化`World`的核心逻辑，主要完成以下工作：

- **设置调用策略 (`InvocationStrategy`)**:
    - 如果没有定义自定义的调用策略，则创建一个默认的`InvocationStrategy`实例，并将其与`World`绑定。

- **核心系统初始化**:
    - 系统集合中的前三个位置（`systems`列表）分别被设置为`ComponentManager`、`EntityManager` 和 `AspectSubscriptionManager`，这些是Artemis的核心系统，默认情况下不可替换。

- **注册和初始化系统**:
    - 遍历所有的系统（包括核心系统和用户自定义的系统），将其添加到`World`的分区系统集合`partition`中，并进行如下逻辑：
        - 设置每个系统的`World`实例。
        - 如果系统是一个`Manager`，调用其`registerManager`方法进行注册。
    - `Injector`初始化`WiredFieldResolver`、`ArtemisFieldResolver`、`AspectFieldResolver`。
        - `WiredFieldResolver`: 
        - `ArtemisFieldResolver`: 负责系统的注入。可以注入顶层的抽象类型系统，如B0继承于A，可以在属性声明时用A，如果有多个继承于A，会导致前面被后面的覆盖（注入不准确）
        - `AspectFieldResolver`:
    - `injector`注入配置`WorldConfiguration`里系统的属性。
    - 调用`WorldConfiguration`里每个系统的`initialize`方法完成初始化。

- **`AspectSubscriptionManager` 处理**:
    - 调用 `asm.processComponentIdentity(NO_COMPONENTS, new BitVector())` 方法，用于处理组件的身份验证。

- **设置和初始化调用策略**:
    - 将系统集合设置到调用策略中，并初始化策略，以确保系统按预期顺序被调用。

### 3. **`Injector` 的初始化 (`CachedInjector.initialize`)**:
在`WorldConfiguration`的`initialize`方法中，`Injector`用于处理依赖注入，具体操作包括：

- **`FieldHandler` 初始化**:
    - `FieldHandler` 负责处理字段注入。它通过不同的`FieldResolver`来解析和注入字段依赖。

- **处理注入依赖**:
    - 遍历所有的`FieldResolver`，将其初始化并与`World`实例关联。如果用户自定义的依赖无法被解析，抛出`InjectionException`异常。


