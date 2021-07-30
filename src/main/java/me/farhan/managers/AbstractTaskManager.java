package me.farhan.managers;

import me.farhan.exceptions.ProcessNotFoundException;
import me.farhan.process.Priority;
import me.farhan.process.Process;
import me.farhan.taskmanager.TaskManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

abstract class AbstractTaskManager implements TaskManager
{
    private final int capacity;
    private AtomicLong pidAtomic;

    public AbstractTaskManager(final int capacity)
    {
        this.capacity = capacity;
        pidAtomic = new AtomicLong(0);
    }

    long getANewProcessId()
    {
        return pidAtomic.incrementAndGet();
    }

    public int getCapacity() {
        return capacity;
    }

    abstract Collection<Process> getActiveProcesses();

    @Override
    public void killAll()
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null) {
            return;
        }
        activeProcesses.stream()
                .parallel()
                .forEach(this::killProcess);
    }

    @Override
    public void killGroup(Priority priority)
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null) {
            return;
        }
        activeProcesses.stream()
                .parallel()
                .filter(process -> process.getPriority() == priority)
                .forEach(this::killProcess);

    }
    @Override
    public void kill(long pid) throws ProcessNotFoundException
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null) {
            return;
        }

        Optional<Process> processOpt = activeProcesses.stream()
                                        .filter(p -> p.getPid() == pid)
                                        .findFirst();
        processOpt.orElseThrow(() -> new ProcessNotFoundException(pid));
        killProcess(processOpt.get());
    }

    private void killProcess(Process process)
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        process.kill();
        activeProcesses.remove(process);
    }

    @Override
    public Collection<Process> list()
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null)
            return Collections.emptyList();
        return Collections.unmodifiableCollection(activeProcesses);
    }
}
