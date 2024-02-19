package com.mygdx.game.tutorial01.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.tutorial01.pojo.Box;

public class BoxInput {

    public static void handleInput(Body box, Box human){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            human.runL();
            box.setLinearVelocity(-5,0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            human.runR();
            box.setLinearVelocity(5, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            box.setLinearVelocity(0, 5);
        }else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            box.setLinearVelocity(-5, 5);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            box.setLinearVelocity(5, 5);
        }else {
            human.stand();
        }
    }
}
