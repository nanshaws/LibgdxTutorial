package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import org.cyl.screen.BaseScreen;

public class TextureAltaScreen extends BaseScreen {
    public TextureAtlas atlas;
    public Texture img;
    public Animation<TextureRegion> runR;
    public Animation<TextureRegion> runL;
    public float speedfps=1/15f;
    public float elapsed_time;
    public float playx=100;
    public float playy=100;
    public BitmapFont font;
    public boolean isRight=true;
    public boolean isLeft=false;

   // public TextureRegion[] miror=TextureRegion.split();
    public TextureAltaScreen(int width, int height) {
        super(width, height);
    }

    @Override
    public void renderChild(float dateTime) {
        elapsed_time+=dateTime;
        handInput();

    }

    private void handInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            isRight=true;
            isLeft=false;
            TextureRegion currentFrame=runR.getKeyFrame(elapsed_time,true);
            if (playx>=540) {
                playx=540;
            }else if (playy>=380){
                playy=380;
            }else if (playx<=100){
                playx=100;
            }else if (playy<=100){
                playy=100;
            }
            playx+=4;
            batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            batch.draw(img,playx,playy);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            isRight=false;
            isLeft=true;
            TextureRegion currentFrame=runL.getKeyFrame(elapsed_time,true);
            if (playx>=540) {
                playx=540;
            }else if (playy>=380){
                playy=380;
            }else if (playx<=100){
                playx=100;
            }else if (playy<=100){
                playy=100;
            }
            playx-=4;
            batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)&&Gdx.input.isKeyPressed(Input.Keys.D)){
            isLeft=false;
            isRight=true;
            TextureRegion currentFrame=runR.getKeyFrame(elapsed_time,true);
            if (playx>=540) {
                playx=540;
            }else if (playy>=380){
                playy=380;
            }else if (playx<=100){
                playx=100;
            }else if (playy<=100){
                playy=100;
            }
            playx+=4;
            playy+=4;
            batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)&&Gdx.input.isKeyPressed(Input.Keys.A)){
            isLeft=true;
            isRight=false;
            TextureRegion currentFrame=runL.getKeyFrame(elapsed_time,true);
            if (playx>=540) {
                playx=540;
            }else if (playy>=380){
                playy=380;
            }else if (playx<=100){
                playx=100;
            }else if (playy<=100){
                playy=100;
            }
            playx-=4;
            playy+=4;
            batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            TextureRegion currentFrame=null;
            if (isRight){
                currentFrame =runR.getKeyFrame(elapsed_time,true);
            }else if (isLeft){
                currentFrame =runL.getKeyFrame(elapsed_time,true);
            }
            if (playx>=540) {
                playx=540;
            }else if (playy>=380){
                playy=380;
            }else if (playx<=100){
                playx=100;
            }else if (playy<=100){
                playy=100;
            }
            playy-=4;
            batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){

            if (isRight){
                TextureRegion  currentFrame=runR.getKeyFrame(elapsed_time,true);
                if (playx>=540) {
                    playx=540;
                }else if (playy>=380){
                    playy=380;
                }else if (playx<=100){
                    playx=100;
                }else if (playy<=100){
                    playy=100;
                }
                playy+=4;
                batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
            }else if (isLeft){
                TextureRegion  currentFrame=runL.getKeyFrame(elapsed_time,true);
                if (playx>=540) {
                    playx=540;
                }else if (playy>=380){
                    playy=380;
                }else if (playx<=100){
                    playx=100;
                }else if (playy<=100){
                    playy=100;
                }
                playy+=4;
                batch.draw(currentFrame,playx,playy,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
            }


        }

    }

    @Override
    public void show() {
        setBgColor(Color.WHITE);
        atlas=new TextureAtlas("animation/run.atlas");


        //6-9
        runR=new Animation(speedfps,atlas.findRegion("09"),atlas.findRegion("07"),atlas.findRegion("08"),atlas.findRegion("09"));
        runR.setFrameDuration(speedfps);
        runR.setPlayMode(Animation.PlayMode.LOOP);
        img=new Texture("02.png");
        font=new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureAtlas atlas1 = new TextureAtlas("animation/run.atlas");
        TextureAtlas.AtlasRegion region = atlas1.findRegion("09");
        TextureAtlas.AtlasRegion region1 = atlas1.findRegion("07");
        region1.flip(true,false);
        TextureAtlas.AtlasRegion region2 = atlas1.findRegion("08");
        region2.flip(true,false);
        TextureAtlas.AtlasRegion region3 = atlas1.findRegion("09");
        region3.flip(true,false);
        runL=new Animation<>(speedfps,region,region1,region2,region3);

    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
