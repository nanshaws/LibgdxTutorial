package com.mygdx.game.tutorial01;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.tutorial01.input.BoxInput;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
        // 在这里处理结束碰撞的逻辑
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // 在这里处理碰撞前的准备工作
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // 在这里处理碰撞后的处理工作
    }
}
