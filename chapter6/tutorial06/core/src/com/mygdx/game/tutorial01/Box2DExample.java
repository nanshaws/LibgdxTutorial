package com.mygdx.game.tutorial01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.tutorial01.input.BoxInput;
import com.mygdx.game.tutorial01.pojo.Box;
import com.mygdx.game.tutorial01.utils.MapUtil;

public class Box2DExample extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body ground;
    private  Box box;
    private float PPM=30f;
    OrthogonalTiledMapRenderer  ren;
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);

        debugRenderer = new Box2DDebugRenderer();

        world = new World(new Vector2(0, -9.8f), true);
        MyContactListener contactListener = new MyContactListener();
        world.setContactListener(contactListener);

        // 创建地面
        BodyDef groundDef = new BodyDef();
        groundDef.type= BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);
        ground = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(500/PPM, 100/PPM);
        ground.createFixture(groundShape, 0);
        groundShape.dispose();

        // 创建箱子
        box=new Box(world,1,1);

        TiledMap map=new TiledMap();
        MapUtil mapUtil=new MapUtil();
        map=mapUtil.getTmxMapLoader();
        ren=new OrthogonalTiledMapRenderer(map);
        FixtureDef fixtureDef=new FixtureDef();
        BodyDef bodyDef=new BodyDef();
        mapUtil.createStatic(map,world,fixtureDef,bodyDef);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // 获取箱子的坐标
        float boxX = box.box.getPosition().x;
        float boxY = box.box.getPosition().y;
        // 根据箱子的坐标设置摄像头的位置
        camera.position.set(boxX, boxY, 0);
        camera.update();
        ren.render();
        ren.setView(camera);
        debugRenderer.render(world, camera.combined);
        debugRenderer.setDrawBodies(false);
        // 检查用户输入并更新箱子位置
        BoxInput.handleInput(box.box,box);
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
