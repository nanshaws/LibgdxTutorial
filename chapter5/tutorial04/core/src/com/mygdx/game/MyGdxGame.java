package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	private Sound sound;
	private Music music;
	private boolean isPlaying;

	@Override
	public void create() {
		// 从文件加载音效
		sound = Gdx.audio.newSound(Gdx.files.internal("run.mp3"));
		// 从文件加载音乐
		music = Gdx.audio.newMusic(Gdx.files.internal("周杰伦-床边故事.mp3"));
		// 设置音乐循环
		music.setLooping(true);
	}

	@Override
	public void render() {
		// 每秒渲染60帧
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// 按下空格键播放音效
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !isPlaying) {
			sound.play();
			isPlaying = true;
			System.out.println("正在播放音效");
		}
		// 按下A键播放音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.A) && !isPlaying) {
			music.play();
			isPlaying = true;
			System.out.println("正在播放音乐:"+music.isPlaying());
		}
		// 按下W键，关闭音效
		else if (Gdx.input.isKeyPressed(Input.Keys.W) && isPlaying) {
			sound.pause();
			isPlaying = false;
			System.out.println("关闭音效");
		}
		// 按下S键关闭音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.S) && music.isLooping()) {
			music.pause();
			System.out.println("暂停音乐:"+music.isPlaying());
		}
		// 按下Y键继续播放音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.Y) && isPlaying) {
			music.play();
			isPlaying = true;
			System.out.println("继续播放音乐:"+music.isPlaying());
		}
	}

	@Override
	public void dispose() {
		// 释放音效和音乐资源
		sound.dispose();
		music.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
