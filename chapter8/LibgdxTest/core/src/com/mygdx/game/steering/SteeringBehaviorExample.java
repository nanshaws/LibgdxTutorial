package com.mygdx.game.steering;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.Texture;

public class SteeringBehaviorExample extends ApplicationAdapter {
    private static final float MAX_FORCE = 1f;
    private static final float MAX_VELOCITY = 100f;

    private Batch batch;
    private Texture characterTexture;
    private OrthographicCamera camera;
    private Viewport viewport;

    private SteeringActor character;
    private SteeringActor target;

    @Override
    public void create() {
        batch = new SpriteBatch();
        characterTexture = new Texture("character.png");

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1080, 920, camera);

        character = new SteeringActor(characterTexture);
        character.setMaxLinearSpeed(MAX_VELOCITY);
        character.setMaxLinearAcceleration(MAX_FORCE);

        Vector2 targetPosition = new Vector2(100, 100);
        target = new SteeringActor(new Texture("target.png"), targetPosition);
        character.setSize(64, 64);
        target.setSize(64, 64);
        // 使用 Arrive 行为替代 Seek 行为
        SteeringBehavior<Vector2> arriveBehavior = new Arrive<>(character, target)
                .setArrivalTolerance(0.001f)  // 设置到达目标的容忍距离
                .setDecelerationRadius(20)   // 在此半径内开始减速
                .setTimeToTarget(0.1f);       // 控制减速响应速度
        character.setSteeringBehavior(arriveBehavior);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        character.applySteering(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        character.draw(batch);
        target.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        characterTexture.dispose();
    }
}