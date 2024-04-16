## 5.1 音频播放

音效用的是Sound，音乐用的是Music

以下是一个简单的混合示例代码：

```
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
		sound = Gdx.audio.newSound(Gdx.files.internal("sound.wav"));
		// 从文件加载音乐
		music = Gdx.audio.newMusic(Gdx.files.internal("周杰伦-床边故事.mp3"));
		// 设置音乐循环
		music.setLooping(true);
		// 设置音效和音乐的初始状态为未播放
		isPlaying = false;
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
		}
		// 按下A键播放音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.A) && !isPlaying && !music.isLooping()) {
			music.play();
			isPlaying = true;
		}
		// 按下W键，关闭音效
		else if (Gdx.input.isKeyPressed(Input.Keys.W) && isPlaying) {
			sound.pause();
			isPlaying = false;
		}
		// 按下S键关闭音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.S) && music.isLooping()) {
			music.pause();
		}
		// 按下U键继续播放音效
		else if (Gdx.input.isKeyPressed(Input.Keys.U) && isPlaying) {
			isPlaying = false;
		}
		// 按下Y键继续播放音乐
		else if (Gdx.input.isKeyPressed(Input.Keys.Y) && isPlaying) {
			music.play();
			isPlaying = true;
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

```

## 5.2 音效管理

在libGDX中，可以使用Sound类来管理音效。首先需要创建一个Sound对象，然后使用它来加载音效文件。接着在游戏循环中更新音效播放并控制音量。

以下是一个简单的音效管理示例代码：

```
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundManagerExample extends ApplicationAdapter {
    private Sound sound;
    private FileHandle soundFile;

    @Override
    public void create() {
        soundFile = Gdx.files.internal("sound.wav");
        sound = Gdx.audio.newSound(soundFile);
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched()) {
            sound.play();
        }
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
    
```



## 5.3 视频播放完整案例代码

由于libGDX不支持直接播放视频，因此需要使用其他库来实现视频播放功能。例如，可以使用libGDX的扩展库libGDX-video库来实现视频播放。以下是一个简单的视频播放示例代码：

```
class VideoExample implements ApplicationListener {
    private VideoPlayer videoPlayer;
    private VideoFrame videoFrame;
    private Texture texture;

    @Override
    public void create() {
        // 创建VideoPlayer实例
        videoPlayer = Gdx.video.newVideoPlayer(Gdx.files.internal("video.mp4"));
        // 创建VideoFrame实例
        videoFrame = new VideoFrame(videoPlayer.getVideoInfo().getWidth(), videoPlayer.getVideoInfo().getHeight(), videoPlayer.getVideoInfo().getFormat());
        // 创建Texture实例
        texture = new Texture(Gdx.files.internal("video.mp4"));
        // 设置视频播放器的渲染目标
        videoPlayer.setRenderTarget(videoFrame);
        // 播放视频
        videoPlayer.play();
    }

    @Override
    public void render() {
        // 每秒渲染60帧
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 渲染视频
        videoPlayer.render(Gdx.graphics.getDeltaTime());
        // 将视频渲染到Texture上
        texture.draw(videoFrame, 0, 0);
        // 绘制Texture
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        texture.bind();
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 4);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        // 释放视频播放器和Texture资源
        videoPlayer.dispose();
        texture.dispose();
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

```

