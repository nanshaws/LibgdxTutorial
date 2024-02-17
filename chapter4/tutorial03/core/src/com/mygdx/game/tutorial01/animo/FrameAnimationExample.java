package com.mygdx.game.tutorial01.animo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class FrameAnimationExample extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private float elapsedTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("t23.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();
        animation = new Animation<>(0.02f, frames);
        elapsedTime = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime, true), 50, 50);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }
}
 