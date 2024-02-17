package com.mygdx.game.tutorial01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Box2DExample extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body ground;
    private Body box;
    private float PPM=30f;
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
        groundShape.setAsBox(50, 1);
        ground.createFixture(groundShape, 0);
        groundShape.dispose();

        // 创建箱子
        BodyDef boxDef = new BodyDef();
        boxDef.type = BodyDef.BodyType.DynamicBody;
        boxDef.position.set(0, 10);
        box = world.createBody(boxDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(1, 1);
        box.createFixture(boxShape, 1);
        boxShape.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        debugRenderer.render(world, camera.combined);

        // 检查用户输入并更新箱子位置
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            box.setLinearVelocity(-5,0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            box.setLinearVelocity(5, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            box.setLinearVelocity(0, 5);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            box.setLinearVelocity(0, -5);
        }
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
