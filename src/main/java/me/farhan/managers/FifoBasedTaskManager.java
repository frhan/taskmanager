package me.farhan.managers;

import me.farhan.process.Process;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

class FifoBasedTaskManager extends QueueBasedTaskManager
{
    public FifoBasedTaskManager(int capacity) {
        super(capacity, new ArrayBlockingQueue<>(capacity));
    }

    @Override
    public long add(Process process)
    {
        Queue<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses.size() == getCapacity()) {
            Process polledProcess = activeProcesses.poll();
            System.out.println("Active process list reached the capacity. " +
                    "Killing the process with PID: " + polledProcess.getPid());
            polledProcess.kill();
        }
        return addAsActiveProcess(process);
    }
}
