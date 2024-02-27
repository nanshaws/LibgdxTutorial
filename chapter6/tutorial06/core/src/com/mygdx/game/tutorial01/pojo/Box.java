package com.mygdx.game.tutorial01.pojo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.tutorial01.input.BoxInput;
import com.mygdx.game.tutorial01.utils.MapUtil;

import static com.mygdx.game.tutorial01.constant.Constant.PPM;

public class Box extends Sprite {
    public float width;
    public float height;
    public boolean isWalking;
    public boolean isRunning;
    public boolean isGround=true;
    public SpriteBatch batch;
    public TextureRegion img;
    private TextureAtlas textureAtlas;
    public Animation<TextureAtlas.AtlasRegion> runL;
    private Animation<TextureAtlas.AtlasRegion> runR;
    private Animation<TextureAtlas.AtlasRegion> stand;
    private Animation<TextureAtlas.AtlasRegion> attack;
    public Body box;
    private float elapsedTime;
    private float speedfps=0.15f;
    private float attackfps=0.0000005f;
    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isGround() {
        return isGround;
    }

    public void setGround(boolean ground) {
        isGround = ground;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public TextureRegion getImg() {
        return img;
    }

    public void setImg(TextureRegion img) {
        this.img = img;
    }


    public Box(World world,float width,float height) {
        this.height=height;
        this.width=width;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(MapUtil.playx,MapUtil.playy);

        box = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        box.createFixture(fixtureDef);

        shape.dispose();
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("human/tian/run/tianRun.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();
        runR = new Animation<>(speedfps, frames);

        textureAtlas = new TextureAtlas(Gdx.files.internal("human/tian/run/tianRun.atlas"));
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion("06");
        region.flip(true,false);
        TextureAtlas.AtlasRegion region1 = textureAtlas.findRegion("07");
        region1.flip(true,false);
        TextureAtlas.AtlasRegion region2 = textureAtlas.findRegion("08");
        region2.flip(true,false);
        TextureAtlas.AtlasRegion region3 = textureAtlas.findRegion("09");
        region3.flip(true,false);
        runL=new Animation<>(speedfps,region,region1,region2,region3);

        textureAtlas = new TextureAtlas(Gdx.files.internal("human/tian/stand/tianstand.atlas"));
        Array<TextureAtlas.AtlasRegion> Standframes = textureAtlas.getRegions();
        stand = new Animation<>(speedfps, Standframes);

        textureAtlas=new TextureAtlas(Gdx.files.internal("human/tian/attack/tianattack.atlas"));
        Array<TextureAtlas.AtlasRegion> attackframes = textureAtlas.getRegions();
        attack=new Animation<>(attackfps,attackframes);
    }
    public void runR(){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(runR.getKeyFrame(elapsedTime, true), (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        batch.end();
    }
    public void stand(){
        batch.begin();
        batch.draw(stand.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        batch.end();
    }

    public void runL(){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(runL.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        batch.end();
    }
    public void jump(){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        if (BoxInput.islefting)
            batch.draw(runL.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        if (BoxInput.isrighting)
            batch.draw(runR.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        else {
            batch.draw(stand.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        }
        batch.end();
    }

    public void attack(){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(attack.getKeyFrame(elapsedTime, true),  (box.getPosition().x+Gdx.graphics.getWidth())/2-30,(box.getPosition().y/2+Gdx.graphics.getHeight())/2-40,60,60);
        batch.end();
    }
}
