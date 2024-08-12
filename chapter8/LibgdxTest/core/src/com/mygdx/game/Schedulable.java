package com.mygdx.game;

interface Schedulable {
    void runTask(float deltaTime);
    int getFrequency();
    int getPriority();
}
