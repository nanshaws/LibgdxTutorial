package com.mygdx.game.tutorial01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.tutorial01.input.InputAdapter;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private InputProcessor inputProcessor;
	private Rectangle player;
	private Rectangle enemy;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		inputProcessor = new InputAdapter();
		Gdx.input.setInputProcessor(inputProcessor);
		player = new Rectangle();
		enemy = new Rectangle();

		// 设置player和enemy的初始位置
		player.x = 0;
		player.y = 0;
		player.width = 50; // 设置player的宽度
		player.height = 50; // 设置player的高度

		enemy.x = 0;
		enemy.y = 30;
		enemy.width = 50; // 设置enemy的宽度
		enemy.height = 50; // 设置enemy的高度
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		if (player.overlaps(enemy)) {
			System.out.println("碰撞了");
		}
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
