package org.cyl.framwork;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.cyl.screen.BaseScreen;

public class BaseGame extends Game {
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected float height=600;
    protected float width=800;

    protected BaseGame(){

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false,width,height);
        camera.update();
    }



    @Override
    public Screen getScreen() {
        return super.getScreen();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera=new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        camera.position.set(width/2,height/2,0);
        camera.update();

    }
    public void setScreen(Screen screen){
        super.setScreen(screen);
        ((BaseScreen)screen).setBatch(batch);
        super.setScreen(screen);
    }

}
