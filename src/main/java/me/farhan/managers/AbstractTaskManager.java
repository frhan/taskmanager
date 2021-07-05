package me.farhan.managers;

import me.farhan.exceptions.ProcessNotFoundException;
import me.farhan.process.Priority;
import me.farhan.process.Process;
import me.farhan.taskmanager.TaskManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

abstract class AbstractTaskManager implements TaskManager
{
    private final int capacity;
    private AtomicLong pidAtomic;
    private ReadWriteLock readWriteLock;

    public AbstractTaskManager(final int capacity)
    {
        this.capacity = capacity;
        pidAtomic = new AtomicLong(0);
        readWriteLock = new ReentrantReadWriteLock();
    }

    Lock getReadLock()
    {
        return readWriteLock.readLock();
    }

    Lock getWriteLock()
    {
        return readWriteLock.readLock();
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
        if (activeProcesses == null)
            return;

        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();
            activeProcesses.forEach(this::killProcess);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public void killGroup(Priority priority)
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null)
            return;

        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();
            activeProcesses.stream()
                    .filter(process -> process.getPriority() == priority)
                    .forEach(this::killProcess);
        }finally {
            writeLock.unlock();
        }
    }
    @Override
    public void kill(long pid) throws ProcessNotFoundException
    {
        Collection<Process> activeProcesses = getActiveProcesses();
        if (activeProcesses == null)
            return;

        Lock writeLock = getWriteLock();
        try {
            writeLock.lock();

            Optional<Process> processOpt = activeProcesses.stream()
                                            .filter(p -> p.getPid() == pid)
                                            .findFirst();
            processOpt.orElseThrow(() -> new ProcessNotFoundException(pid));

            killProcess(processOpt.get());
        }finally {
            writeLock.unlock();
        }
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

        Lock readLock = getReadLock();
        try {
            readLock.lock();
            return Collections.unmodifiableCollection(activeProcesses);
        }finally {
            readLock.unlock();
        }
    }
}
