package me.farhan.managers;

import me.farhan.process.Process;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

abstract class QueueBasedTaskManager extends AbstractTaskManager
{
    private final Queue<Process> activeProcesses;

    QueueBasedTaskManager(int capacity, Queue<Process> activeProcesses) {
        super(capacity);
        this.activeProcesses = activeProcesses;
    }

    @Override
    Queue<Process> getActiveProcesses() {
        return activeProcesses;
    }

    @Override
    public void killAll()
    {
        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();
            while (!activeProcesses.isEmpty()) {
                Process polledProcess = activeProcesses.poll();
                polledProcess.kill();
            }
        } finally {
            writeLock.unlock();
        }
    }
}
