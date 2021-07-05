package me.farhan.managers;

import me.farhan.process.ActiveProcess;
import me.farhan.process.Process;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

class DefaultTaskManager extends AbstractTaskManager
{
    private final List<Process> activeProcesses;

    DefaultTaskManager(int capacity)
    {
        super(capacity);
        this.activeProcesses = new CopyOnWriteArrayList<>();
    }

    @Override
    Collection<Process> getActiveProcesses() {
        return activeProcesses;
    }

    @Override
    public long add(Process process)
    {
        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();
            if (this.activeProcesses.size() == getCapacity()) {
                System.out.println("Active process list reached the capacity.Skip Adding the process");
                return -1;
            }

            ActiveProcess activeProcess = new ActiveProcess( this.getANewProcessId()
                                                           , process.getPriority());
            this.activeProcesses.add(activeProcess);
            System.out.println("Process added with PID: " + activeProcess.getPid()+" and Priority: "+ activeProcess.getPriority());
            return activeProcess.getPid();
        }finally {
            writeLock.unlock();
        }
    }
}
