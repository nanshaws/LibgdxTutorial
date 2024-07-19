package com.nanshanws.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nanshanws.game.components.AnimaComponent;
import com.nanshanws.game.systems.AnimationSystem;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Engine entityEngine;
	private AnimationSystem animationSystem;
	private Animation<TextureRegion> currentAnimation;
	private AnimaComponent animaComponent;
	private Animation animation;
	private Entity entity;
	private TextureAtlas textureAtlas;
	@Override
	public void create () {

		textureAtlas = new TextureAtlas(Gdx.files.internal("t23.atlas"));
		Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();
		animation = new Animation<>(0.02f, frames);

		TextureAtlas.AtlasRegion[] atlasRegions = frames.toArray(TextureAtlas.AtlasRegion.class);
        // 创建 Animation 对象并设置到 currentAnimation
		currentAnimation = new Animation<>(0.02f, atlasRegions);
		// 初始化SpriteBatch和OrthographicCamera
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// 初始化EntityEngine和AnimationSystem
		entityEngine = new Engine();
		animationSystem = new AnimationSystem(batch, camera);
		entityEngine.addSystem(animationSystem);

		animaComponent=new AnimaComponent(currentAnimation,0);

		//创建实体
		entity = entityEngine.createEntity();
		entity.add(animaComponent);
		entityEngine.addEntity(entity);
        
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		entityEngine.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}
}
