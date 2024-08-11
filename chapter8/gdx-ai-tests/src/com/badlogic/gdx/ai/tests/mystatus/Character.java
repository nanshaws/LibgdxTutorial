package com.badlogic.gdx.ai.tests.mystatus;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.msg.Telegram;

class Character {
    private StateMachine<Character, CharacterState> stateMachine;

    public Character() {
        stateMachine = new DefaultStateMachine<>(this, CharacterState.IDLE);
    }

    public void update() {
        stateMachine.update();
    }

    public void changeState(CharacterState newState) {
        stateMachine.changeState(newState);
    }

    public StateMachine<Character, CharacterState> getStateMachine() {
        return stateMachine;
    }

    public void performAction(String action) {
        System.out.println("Performing action: " + action);
    }
}