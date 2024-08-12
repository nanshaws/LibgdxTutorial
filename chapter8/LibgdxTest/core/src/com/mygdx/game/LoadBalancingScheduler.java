package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class LoadBalancingScheduler {
    private List<Schedulable> tasks = new ArrayList<>();
    private Queue<SchedulerTask> taskQueue = new PriorityQueue<>();
    private int frameCounter = 0;

    public void addTask(Schedulable task) {
        tasks.add(task);
    }

    public void update(float deltaTime) {
        frameCounter++;
        taskQueue.clear();
        for (Schedulable task : tasks) {
            if (frameCounter % task.getFrequency() == 0) {
                taskQueue.add(new SchedulerTask(task, deltaTime));
            }
        }

        while (!taskQueue.isEmpty()) {
            SchedulerTask schedulerTask = taskQueue.poll();
            schedulerTask.run();
        }
    }

    private static class SchedulerTask implements Comparable<SchedulerTask> {
        private Schedulable task;
        private float deltaTime;

        public SchedulerTask(Schedulable task, float deltaTime) {
            this.task = task;
            this.deltaTime = deltaTime;
        }

        @Override
        public int compareTo(SchedulerTask o) {
            return Integer.compare(o.task.getPriority(), this.task.getPriority());
        }

        public void run() {
            task.runTask(deltaTime);
        }
    }
}