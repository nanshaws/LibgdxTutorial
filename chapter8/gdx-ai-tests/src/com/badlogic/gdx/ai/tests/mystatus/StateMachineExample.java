package com.badlogic.gdx.ai.tests.mystatus;

public class StateMachineExample {
    public static void main(String[] args) {
        Character character = new Character();

        // 演示状态切换
        character.update(); // Initially idle

        character.changeState(CharacterState.WALKING);
        character.update(); // Walking

        character.changeState(CharacterState.RUNNING);
        character.update(); // Running

        character.changeState(CharacterState.IDLE);
        character.update(); // Back to idle
    }
}