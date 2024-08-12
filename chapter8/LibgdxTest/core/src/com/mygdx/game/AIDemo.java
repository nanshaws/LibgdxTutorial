package com.mygdx.game;

public class AIDemo {
    public static void main(String[] args) {
        LoadBalancingScheduler scheduler = new LoadBalancingScheduler();

        // Create tasks with different frequencies and priorities
        Schedulable task1 = new AI_Task("Task1", 2, 1);
        Schedulable task2 = new AI_Task("Task2", 3, 2);
        Schedulable task3 = new AI_Task("Task3", 5, 3);

        scheduler.addTask(task1);
        scheduler.addTask(task2);
        scheduler.addTask(task3);

        // Simulate 10 frames update
        for (int i = 0; i < 10; i++) {
            System.out.println("Frame: " + i);
            scheduler.update(1.0f / 60.0f);  // Assuming 60 FPS
            System.out.println();
            try {
                Thread.sleep(1000 / 60); // Simulate frame duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}