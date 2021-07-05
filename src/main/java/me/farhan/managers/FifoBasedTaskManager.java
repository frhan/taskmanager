package me.farhan.managers;

import me.farhan.process.ActiveProcess;
import me.farhan.process.Process;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;

class FifoBasedTaskManager extends QueueBasedTaskManager
{
    public FifoBasedTaskManager(int capacity) {
        super(capacity, new ArrayBlockingQueue<>(capacity));
    }

    @Override
    public long add(Process process)
    {
        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();

            Queue<Process> activeProcesses = getActiveProcesses();
            if (activeProcesses.size() == getCapacity()) {
                Process polledProcess = activeProcesses.poll();
                System.out.println("Active process list reached the capacity. " +
                        "Killing the process with PID: " + polledProcess.getPid());
                polledProcess.kill();
            }

            ActiveProcess activeProcess = new ActiveProcess( this.getANewProcessId()
                                                           , process.getPriority());
            activeProcesses.offer(activeProcess);
            System.out.println("Process added with PID: " + activeProcess.getPid()+" and Priority: "+ activeProcess.getPriority());
            return activeProcess.getPid();
        }finally {
            writeLock.unlock();
        }
    }

}
