package com.badlogic.gdx.ai.tests.myMessage;
import com.badlogic.gdx.ai.msg.*;
public class AIEntity implements Telegraph {
    private String name;

    public AIEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        MessageType messageType = MessageType.values()[msg.message];
        switch (messageType) {
            case ATTACK:
                System.out.println(name + " received a ATTACK message with extra info: " + msg.extraInfo);
                break;
            case DEFEND:
                System.out.println(name + " received a DEFEND message with extra info: " + msg.extraInfo);
                break;
            case PATROL:
                System.out.println(name + " received a PATROL message with extra info: " + msg.extraInfo);
                break;
            default:
                return false;
        }
        return true;
    }
}