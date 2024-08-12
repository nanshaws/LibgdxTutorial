package com.mygdx.game;

class AI_Task implements Schedulable {
    private String name;
    private int frequency;
    private int priority;

    public AI_Task(String name, int frequency, int priority) {
        this.name = name;
        this.frequency = frequency;
        this.priority = priority;
    }

    @Override
    public void runTask(float deltaTime) {
        System.out.println("Running task: " + name + " with deltaTime: " + deltaTime);
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
