package com.badlogic.gdx.ai.tests.mystatus;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

enum CharacterState implements State<Character> {
    IDLE {
        @Override
        public void update(Character character) {
            System.out.println("Character is idling…");
        }
    },
    WALKING {
        @Override
        public void enter(Character character) {
            System.out.println("Character starts walking.");
        }

        @Override
        public void update(Character character) {
            character.performAction("Walking");
        }

        @Override
        public void exit(Character character) {
            System.out.println("Character stops walking.");
        }
    },
    RUNNING {
        @Override
        public void enter(Character character) {
            System.out.println("Character starts running.");
        }

        @Override
        public void update(Character character) {
            character.performAction("Running");
        }

        @Override
        public void exit(Character character) {
            System.out.println("Character stops running.");
        }
    };

    @Override
    public void enter(Character entity) {
    }

    @Override
    public void update(Character entity) {
    }

    @Override
    public void exit(Character entity) {
    }

    @Override
    public boolean onMessage(Character entity, Telegram telegram) {
        return false;
    }
}