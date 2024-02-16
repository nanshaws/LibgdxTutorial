package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.cyl.screen.BaseScreen;

public class MyGdxScreen extends BaseScreen {
    private Texture player;
//    protected float speed=0.3f;
//
//    public void setSpeed(float speed) {
//        this.speed = speed;
//    }

    public MyGdxScreen(int width, int height) {
        super(width,height);
        setBgColor(Color.WHITE);

    }

    @Override
    public void show() {
        player=new Texture("01.png");
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }


    @Override
    public void renderChild(float dateTime) {
        batch.setColor(Color.WHITE);
        batch.draw(player,0,0);
        handleInput();
    }

    private void handleInput() {
//        if (Gdx.input.isKeyPressed(Input.Keys.W)){
//            player=new Texture("");
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)){
//           player=new Texture("06.png");
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)){
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)){
//
//        }
//        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
//            player=new Texture("01.png");
//        }

    }


}
