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
