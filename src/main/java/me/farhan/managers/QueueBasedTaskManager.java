package me.farhan.managers;

import me.farhan.process.ActiveProcess;
import me.farhan.process.Process;

import java.util.Queue;

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
        while (!activeProcesses.isEmpty()) {
            Process polledProcess = activeProcesses.poll();
            polledProcess.kill();
        }
    }

    long addAsActiveProcess(Queue<Process> activeProcesses, Process process)
    {
        ActiveProcess activeProcess = new ActiveProcess( this.getANewProcessId()
                                                        , process.getPriority());
        activeProcesses.offer(activeProcess);
        System.out.println("Process added with PID: " + activeProcess.getPid()+" and Priority: "+ activeProcess.getPriority());
        return activeProcess.getPid();
    }
}
