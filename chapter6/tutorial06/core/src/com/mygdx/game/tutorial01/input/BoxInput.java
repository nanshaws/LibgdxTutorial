package com.mygdx.game.tutorial01.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.tutorial01.pojo.Box;

public class BoxInput {

    public static boolean islefting=false;
    public static boolean isrighting=false;
    public static int jumpCount = 0; // 添加跳跃次数变量
    public static boolean canJump = true; // 添加是否可以跳跃的变量

    public static void handleInput(Body box, Box human){
        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canJump) { // 添加是否可以跳跃的判断
                if (Math.abs(box.getLinearVelocity().y) < 0.01) { // 检查是否在空中
                    human.jump();
                    box.setLinearVelocity(-5, 5);
                    jumpCount++; // 跳跃次数加1
                    if (jumpCount >= 2) {
                        canJump = false; // 跳跃次数达到2次时，禁止跳跃
                    }
                }
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canJump) { // 添加是否可以跳跃的判断
                if (Math.abs(box.getLinearVelocity().y) < 0.01) { // 检查是否在空中
                    human.jump();
                    box.setLinearVelocity(5, 5);
                    jumpCount++; // 跳跃次数加1
                    if (jumpCount >= 2) {
                        canJump = false; // 跳跃次数达到2次时，禁止跳跃
                    }
                }
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            human.runL();
            islefting=true;
            isrighting=false;
            box.setLinearVelocity(-5,0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            human.runR();
            islefting=false;
            isrighting=true;
            box.setLinearVelocity(5, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canJump) { // 添加是否可以跳跃的判断
                if (Math.abs(box.getLinearVelocity().y) < 0.01) { // 检查是否在空中
                    human.stand();
                    box.setLinearVelocity(0, 5);
                    jumpCount++; // 跳跃次数加1
                    if (jumpCount >= 2) {
                        canJump = false; // 跳跃次数达到2次时，禁止跳跃
                    }
                }
            }
        } else {
            human.stand();
        }

        // 添加重力
        box.applyForceToCenter(0, -9.8f, true);
        if (!canJump && Math.abs(box.getLinearVelocity().y) < 0.01) {
            // 人物落地后清零跳跃次数，并允许下一次跳跃
            jumpCount = 0;
            canJump = true;
        }
    }

}
