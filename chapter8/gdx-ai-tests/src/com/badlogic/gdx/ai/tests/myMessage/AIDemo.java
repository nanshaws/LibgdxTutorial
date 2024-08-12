package com.badlogic.gdx.ai.tests.myMessage;

import com.badlogic.gdx.ai.msg.MessageDispatcher;

public class AIDemo {
    public static void main(String[] args) {
        // 创建实体和消息派发器
        AIEntity entity1 = new AIEntity("Entity1");
        AIEntity entity2 = new AIEntity("Entity2");

        MessageDispatcher dispatcher = new MessageDispatcher();

        // 注册实体到消息派发器
        dispatcher.addListener(entity1, MessageType.ATTACK.ordinal());
        dispatcher.addListener(entity2, MessageType.DEFEND.ordinal());
        dispatcher.addListener(entity2, MessageType.PATROL.ordinal());

        // 发送消息
        dispatcher.dispatchMessage(MessageType.ATTACK.ordinal());
        dispatcher.dispatchMessage(entity1, entity2, MessageType.PATROL.ordinal(), "ExtraInfo", false);
    }
}