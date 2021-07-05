package me.farhan.managers;

import me.farhan.process.ActiveProcess;
import me.farhan.process.Process;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;

class PriorityBasedTaskManager extends QueueBasedTaskManager {

    public PriorityBasedTaskManager(int capacity)
    {
        super(capacity, new PriorityBlockingQueue<>(capacity));
    }

    @Override
    public long add(Process process)
    {
        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();
            Queue<Process> activeProcesses = getActiveProcesses();

            if (activeProcesses.size() == getCapacity()) {
                //process list with lowest priority and oldest timestamp
                Optional<Process> processOpt = activeProcesses.stream()
                                                .filter(p -> process.compareTo(p) < 0)
                                                .sorted(Process.processCreationTimeComparator())
                                                .findFirst();

                if (!processOpt.isPresent()) {
                    System.out.println("Active process list reached the capacity." +
                            "No process has lower capacity than this process." +
                            "Skip adding the process");
                    return -1;
                }

                Process lowestPriorityProcess = processOpt.get();
                lowestPriorityProcess.kill();
                activeProcesses.remove(lowestPriorityProcess);
            }

            ActiveProcess activeProcess = new ActiveProcess(this.getANewProcessId(),
                                                            process.getPriority());
            activeProcesses.offer(activeProcess);
            System.out.println("Process added with PID: " + activeProcess.getPid()+" and Priority: "+ activeProcess.getPriority());
            return activeProcess.getPid();
        } finally {
            writeLock.unlock();
        }
    }
}
