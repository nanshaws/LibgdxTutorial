package com.nanshaws;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SimpleTest extends ApplicationAdapter {
	/** the camera **/
	OrthographicCamera camera;
	RayHandler rayHandler;
	World world;

	@Override
	public void create() {
		camera = new OrthographicCamera(48, 32);
		camera.update();
		world = new World(new Vector2(0, -10), true);
		rayHandler = new RayHandler(world);
        PointLight pointLight = new PointLight(rayHandler, 32);
        pointLight.setColor(1f, 1f, 1f, 1f); // 使用RGBA值，白色光且全不透明
    }

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		rayHandler.setCombinedMatrix(camera);
		rayHandler.updateAndRender();
	}
}
