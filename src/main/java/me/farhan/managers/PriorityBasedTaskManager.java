package me.farhan.managers;

import me.farhan.process.Process;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

class PriorityBasedTaskManager extends QueueBasedTaskManager {

    public PriorityBasedTaskManager(int capacity)
    {
        super(capacity, new PriorityBlockingQueue<>(capacity));
    }

    @Override
    public long add(Process process)
    {
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
        return addAsActiveProcess(activeProcesses, process);
    }
}
