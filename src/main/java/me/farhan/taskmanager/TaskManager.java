package me.farhan.taskmanager;

import me.farhan.exceptions.ProcessNotFoundException;
import me.farhan.process.Priority;
import me.farhan.process.Process;

import java.util.Collection;

public interface TaskManager
{
    int DEFAULT_CAPACITY = 10;

    /**
     * @param process
     * @return  process Id(PID), if pid < 0, that means the process couldn't add successfully to the process manager
     */
    long add(Process process);

    /**
     *
     * @return list of active process
     */
    Collection<Process> list();

    /**
     * Kill all active processes
     */
    void killAll();

    /**
     * Kill all active processes with given priority
     * @param priority
     */
    void killGroup(Priority priority);

    /**
     * Kill all active processes with given pid
     * @param pid
     * @throws ProcessNotFoundException
     */
    void kill(long pid) throws ProcessNotFoundException;
}
