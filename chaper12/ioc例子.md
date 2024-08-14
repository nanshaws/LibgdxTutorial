1. 进行world的构造
```java
public class WorldConstructor {

    private static final int LOGIC = 10;
    private static final int PRE_ENTITY_RENDER_PRIORITY = 6;
    private static final int ENTITY_RENDER_PRIORITY = 5;
    private static final int POST_ENTITY_RENDER_PRIORITY = 4;
    private static final int DECORATION_PRIORITY = 3;
    private static final int UI = 0;

    private static WorldConfiguration getWorldConfiguration(ClientConfiguration clientConfiguration, ScreenManager screenManager, DefaultAOAssetManager assetManager) {
        return new WorldConfigurationBuilder()
                // Sistemas de uso global (no necesitan prioridad porque son pasivos)
                .with(clientConfiguration,
                        screenManager)

                // register all screens
                .with(Arrays.stream(ScreenEnum.values())
                        .map(ScreenEnum::get)
                        .map(BaseSystem.class::cast)
                        .toArray(BaseSystem[]::new))

                // Network system (no necesita prioridad porque es asincrónico, funciona por callbacks)
                .with(new ClientSystem(),
                        new ClientResponseProcessor(),
                        new GameNotificationProcessor())

                // Sistemas de alta prioridad
                .with(HIGH,
                        new TimeSync(),
                        new SuperMapper(),
                        new LocalReferenceSystem(),
                        new ClearSystem())

                .with(LOGIC,
                        new IntervalSystem(),

                        // Player component.movement
                        new PlayerInputSystem(),
                        new MovementProcessorSystem(),
                        new MovementAnimationSystem(),
                        new IdleAnimationSystem(),
                        new MovementSystem(),
                        new PlayerSystem(),

                        // Camera
                        new CameraSystem(),
                        new CameraFocusSystem(),
                        new CameraMovementSystem(),
                        new CameraShakeSystem(),

                        // Logic systems
                        new NetworkedEntitySystem(),
                        new AttackAnimationSystem(),
                        new SoundSytem(),
                        new TiledMapSystem(),
                        new AnimationsSystem(),
                        new DescriptorsSystem(),
                        new MessageSystem(),
                        new MapSystem(),
                        new MusicSystem(),
                        new ObjectSystem(),
                        new ParticlesSystem(),
                        new SoundsSystem(),
                        new SpellsSystem(),
                        new FontsSystem(),
                        new PlayerActionSystem(),
                        new InputSystem(),
                        new ScreenSystem(),
                        new WorldSystem())

                // Rendering
                .with(PRE_ENTITY_RENDER_PRIORITY,
                        new ClearScreenSystem(),
                        new MapGroundRenderingSystem(),
                        new ObjectRenderingSystem(),
                        new TargetRenderingSystem(),
                        new NameRenderingSystem())

                .with(ENTITY_RENDER_PRIORITY,
                        new EffectRenderingSystem(),
                        new CharacterRenderingSystem(),
                        new WorldRenderingSystem())

                .with(POST_ENTITY_RENDER_PRIORITY,
                        new CombatRenderingSystem(),
                        new DialogRenderingSystem(),
                        new MapLastLayerRenderingSystem())

                .with(DECORATION_PRIORITY,
                        new StateRenderingSystem(),
                        new CharacterStatesRenderingSystem(),
                        new BatchRenderingSystem())

                // UI
                .with(UI,
                        new MouseSystem(),
                        new CursorSystem(),
                        new InventorySystem(),
                        new SpellSystem(),
                        new ActionBarSystem(),
                        new ConsoleSystem(),
                        new DialogSystem(),
                        new StatsSystem(),
                        new UserSystem(),
                        new UserInterfaceSystem())

                // Otros sistemas
                .with(new MapManager(),
                        new TagManager(),
                        new UuidEntityManager())

                .build()
                .register(assetManager);
    }

    /**
     * Construye el Artemis World, inicializa e inyecta sistemas.
     * Este método es bloqueante.
     */
    public static World create(ClientConfiguration clientConfiguration,
                               ScreenManager screenManager, DefaultAOAssetManager assetManager) {
        return new World(getWorldConfiguration(clientConfiguration, screenManager, assetManager));
    }
}
```

2. 系统内部注入其他服务
```java
// 注入的话，需要带上@Wire
@Wire
public class TimeSync extends BaseSystem {

    private static final int SEND_REQUEST_EVERY_X_IN_SEGS = 60;
    private ClientSystem client;
    private int requestId;
    private long sendTime;

    private long rtt;
    private long timeOffset;

    private float time = 0;

    /**
     * Returns a message to be sent, which should be sent immediately as the send time is tracked.
     */
    private TimeSyncRequest send() {
        TimeSyncRequest request = TimeSyncRequest.getNextRequest();
        requestId = request.requestId;
        sendTime = TimeUtils.millis();
        return request;
    }

    private void sendRequest() {
        client.send(send());
    }

    public void receive(TimeSyncResponse response) {
        long receiveTime = TimeUtils.millis();

        if (response.requestId == requestId) {
            rtt = (receiveTime - sendTime) - (response.sendTime - response.receiveTime);
            timeOffset = ((response.receiveTime - sendTime) + (response.sendTime - receiveTime)) / 2;
        }
    }

    public long getRtt() {
        return rtt;
    }

    public long getTimeOffset() {
        return timeOffset;
    }

    public long getMaxTimeOffsetError() {
        return rtt / 2;
    }

    @Override
    protected void processSystem() {
        float delta = getWorld().getDelta();
        time -= delta;
        if (time < 0) {
            time = SEND_REQUEST_EVERY_X_IN_SEGS;
            sendRequest();
        }
    }
}
```