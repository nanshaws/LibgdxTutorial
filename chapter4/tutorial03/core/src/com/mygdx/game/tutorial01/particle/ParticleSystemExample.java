package com.mygdx.game.tutorial01.particle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleSystemExample extends ApplicationAdapter {
    private SpriteBatch batch;
    private ParticleEffect particleEffect;

    @Override
    public void create () {
        batch = new SpriteBatch();

        // 创建粒子效果
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("fire.p"), Gdx.files.internal("particle"));
        particleEffect.start();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 更新和绘制粒子效果
        particleEffect.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        particleEffect.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        particleEffect.dispose();
    }
}
