package org.cyl.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseScreen implements Screen {

    public SpriteBatch batch;
    private Color bgColor;
    protected BaseScreen(int width,int height){
        bgColor=new Color();
    }

    @Override
    public void render(float dateTime) {
        Gdx.gl.glClearColor(bgColor.r,bgColor.g,bgColor.b,bgColor.a);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        batch.begin();
        renderChild(dateTime);
        batch.end();
    }

    public abstract void renderChild(float dateTime);

    public void setBatch(SpriteBatch batch){
        this.batch=batch;
   }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

}
