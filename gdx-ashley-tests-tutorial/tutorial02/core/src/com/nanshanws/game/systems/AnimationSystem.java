package com.nanshanws.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nanshanws.game.components.AnimaComponent;



public class AnimationSystem extends EntitySystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ImmutableArray<Entity> entities;

    public AnimationSystem(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
    }


    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimaComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (Entity entity : entities) {
            AnimaComponent animationComponent = ComponentMapper.getFor(AnimaComponent.class).get(entity);
            if (animationComponent.currentAnimation != null) {
                animationComponent.stateTime += deltaTime;
                TextureRegion keyFrame = animationComponent.currentAnimation.getKeyFrame(animationComponent.stateTime,true);
                batch.begin();
                batch.draw(keyFrame, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                batch.end();
            }
        }
    }
}
