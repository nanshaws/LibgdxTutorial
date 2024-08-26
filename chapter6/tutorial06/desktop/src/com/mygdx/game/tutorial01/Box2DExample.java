package com.mygdx.game.tutorial01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Box2DExample extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body body;
    private OrthographicCamera camera;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        // 设置摄像机尺寸。假设是一个32单位宽的世界。
        camera = new OrthographicCamera(32, 18);
        camera.position.set(0, 0, 0);
        camera.update();
        // 创建一个动态物体
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0, 5);

        body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 1); // 创建一个1x1的方形
        body.createFixture(box, 1);
        box.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 模拟世界
        world.step(1 / 60f, 6, 2);

        // 通过不同方式影响物体运动
        applyForce();    // 施加力
        applyImpulse();  // 施加冲量
        setVelocity();   // 设置速度

        // 渲染
        debugRenderer.render(world, camera.combined);
    }

    private void applyForce() {
        // 向右施加一个持续的力
        float forceMagnitude = 10.0f;
        body.applyForceToCenter(new Vector2(forceMagnitude, 0), true);
    }

    private void applyImpulse() {
        // 立即向上施加一个冲量
        float impulseMagnitude = 5.0f;
        body.applyLinearImpulse(new Vector2(0, impulseMagnitude), body.getWorldCenter(), true);
    }

    private void setVelocity() {
        // 直接将物体的速度设置为向右的5单位/秒
        body.setLinearVelocity(new Vector2(5, 0));
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Box2D Example");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Box2DExample(), config);
    }
}

