package com.badlogic.gdx.ai.tests.mybtree;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.Repeat;
import com.badlogic.gdx.ai.btree.leaf.Success;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;

public class BehaviorTreeExample {
    public static void main(String[] args) {
        // 创建角色实例
        Character character = new Character();

        // 创建行为树
        BehaviorTree<Character> behaviorTree = new BehaviorTree<>(createBehaviorTreeStructure(), character);

        // 简单的游戏循环
        for (int i = 0; i < 10; i++) {
            // 步进行为树的逻辑
            behaviorTree.step();
        }
    }

    private static Task<Character> createBehaviorTreeStructure() {
        Sequence<Character> rootSequence = new Sequence<>();
        // 明确指定Repeat类的泛型类型参数
        rootSequence.addChild(new Repeat<Character>(new ConstantIntegerDistribution(1), new PrintHelloTask()));  // 任务将被执行一次
        // 明确指定Success类的泛型类型参数
        rootSequence.addChild(new Success<Character>());  // 总是返回成功

        return rootSequence;
    }
}