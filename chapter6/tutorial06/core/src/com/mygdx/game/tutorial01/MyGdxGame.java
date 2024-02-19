package com.mygdx.game.tutorial01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body playerBody;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("01.png");

        // 创建一个正交投影相机
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 创建 Box2D 世界
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // 创建人物
        createPlayer();
    }

    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        playerBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(20f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        playerBody.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void render() {
        // 清屏
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 更新 Box2D 世界
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // 更新相机
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // 处理输入
        handleInput();

        // 绘制人物
        batch.begin();
        batch.draw(img, playerBody.getPosition().x - 20, playerBody.getPosition().y - 20, 40, 40);
        batch.end();

        // 调试渲染 Box2D 世界
        debugRenderer.render(world, camera.combined);
    }

    private void handleInput() {
        // 人物移动速度
        float speed = 200;

        // 处理键盘输入
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerBody.applyForceToCenter(-speed, 0, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerBody.applyForceToCenter(speed, 0, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerBody.applyForceToCenter(0, speed * 5, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerBody.applyForceToCenter(0, -speed, true);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
