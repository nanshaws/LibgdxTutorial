package com.badlogic.gdx.ai.tests.mybtree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

class PrintHelloTask extends LeafTask<Character> {
    @Override
    public Status execute() {
        // 获取关联的角色对象
        Character character = getObject();
        character.sayHello();
        return Status.SUCCEEDED;
    }
    
    @Override
    protected Task<Character> copyTo(Task<Character> task) {
        return task;
    }
}