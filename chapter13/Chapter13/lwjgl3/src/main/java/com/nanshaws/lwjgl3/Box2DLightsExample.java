package com.nanshaws.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import box2dLight.RayHandler;
import box2dLight.PointLight;

public class Box2DLightsExample extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private RayHandler rayHandler;
    private Body body;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new com.badlogic.gdx.math.Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(32, 18); // This assumes 32x18 units camera. Adjust for your needs.

        // Create a box object in the world
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0, 5);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 1);
        body.createFixture(box, 1);
        box.dispose();

        // Create the ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyType.StaticBody;
        groundBodyDef.position.set(0, -1); // Set this to place the ground correctly

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(15, 0.5f); // Width and height of the ground

        groundBody.createFixture(groundBox, 0);
        groundBox.dispose();

        // Set up the ray handler
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.5f); // Ambient light in the scene
        rayHandler.setCulling(true);
        RayHandler.useDiffuseLight(true);

        // Create a point light
        new PointLight(rayHandler, 128, new Color(1, 1, 1, 1), 20, 6, 5);
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        camera.update();
        debugRenderer.render(world, camera.combined);

        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        rayHandler.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("Box2DLights Example");
        config.setWindowedMode(800,480);  // 宽度，像素

        // 启动应用程序
        new Lwjgl3Application(new Box2DLightsExample(), config);
    }
}
