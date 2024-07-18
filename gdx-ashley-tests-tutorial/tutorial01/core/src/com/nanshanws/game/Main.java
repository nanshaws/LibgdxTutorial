package com.nanshanws.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.nanshanws.game.components.PositionComponent;
import com.nanshanws.game.components.VelocityComponent;
import com.nanshanws.game.systems.MovementSystem;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        Listener listener = new Listener();
        engine.addEntityListener(listener);

        MovementSystem movementSystem = new MovementSystem();
        engine.addSystem(movementSystem);

        /**
         * 批量添加实体
         */
        for (int i = 0; i < 10; i++) {
            Entity entity = engine.createEntity();
            entity.add(new PositionComponent(10, 0));
            entity.add(new VelocityComponent(10, 2));

            engine.addEntity(entity);
        }

        System.out.println("MovementSystem has: " + movementSystem.entities.size() + " entities.");

        for (int i = 0; i < 10; i++) {
            engine.update(0.25f);

        }

        /**
         * 一般基础从单独开始添加
         * 单独添加实体
         */
        System.out.println("单独添加实体");
        Entity entity = engine.createEntity();
        entity.add(new PositionComponent(10, 0));
        entity.add(new VelocityComponent(10, 2));
        engine.addEntity(entity);
        //单独给实体添加引擎
        MovementSystem movementSystem1 = new MovementSystem();
        engine.addSystem(movementSystem1);
        //单独改变启动引擎
        engine.update(3f);
        //单独实体销毁移动引擎
        System.out.println("单独实体销毁移动引擎");
        engine.removeSystem(movementSystem1);

        //销毁所有实体
        engine.removeAllEntities();

        engine.removeEntityListener(listener);




    }
    public static class Listener implements EntityListener {

        @Override
        public void entityAdded (Entity entity) {
            System.out.println("实体添加 " + entity);
        }

        @Override
        public void entityRemoved (Entity entity) {
            System.out.println("实体删除 " + entity);
        }
    }
}
