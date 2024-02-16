# chapter2教程



## 2.2 渲染循环

在Android项目中创建一个类，例如"MyGdxGame"，并继承自ApplicationAdapter。重写create()和render()方法：

```
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends ApplicationAdapter {
    @Override
    public void create() {
        // 初始化代码
    }

    @Override
    public void render() {
        // 更新和绘制游戏逻辑
    }
}

```

## 2.3 精灵与纹理

在create()方法中加载纹理，并创建一个Sprite对象：

```
private Texture texture;
private Sprite sprite;

@Override
public void create() {
    texture = new Texture("my_image.png");
    sprite = new Sprite(texture);
}

```

在render()方法中绘制精灵：

```
@Override
public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    sprite.draw(batch);
}

```

## 2.4 输入处理

在create()方法中设置输入处理器：

```
private InputProcessor inputProcessor;

@Override
public void create() {
    // ...其他代码...
    inputProcessor = new InputAdapter();
    Gdx.input.setInputProcessor(inputProcessor);
}

```

创建一个实现InputProcessor接口的类，例如"InputAdapter"：

```
package com.mygdx.game.tutorial01.input;

import com.badlogic.gdx.InputProcessor;

public class InputAdapter implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("按下键:"+keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("上升键:"+keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("字符输入:"+character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("触摸屏幕:"+"x:"+screenX+"y:"+screenY+"p:"+pointer+"b:"+button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // 处理触摸屏幕抬起事件
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // 处理触摸屏幕拖动事件
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // 处理鼠标移动事件
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

}

```

## 2.5 碰撞检测

使用libgdx内置的Rectangle类进行简单的矩形碰撞检测：

```
private Rectangle player;
private Rectangle enemy;

@Override
public void create() {
    // ...其他代码...
    player = new Rectangle();
    enemy = new Rectangle();
}

@Override
public void render() {
    // ...其他代码...
    if (player.overlaps(enemy)) {
        // 处理碰撞事件
    }
}

```

