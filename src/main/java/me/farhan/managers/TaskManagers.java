package me.farhan.managers;

import me.farhan.taskmanager.TaskManager;

public final class TaskManagers
{
    private TaskManagers()
    {
      throw new AssertionError("No Instance");
    }

    /**
     *
     * @return Priority based task manager with default capacity of 10
     */
    public static TaskManager getPriorityBasedTaskManager(){
        return getPriorityBasedTaskManager(TaskManager.DEFAULT_CAPACITY);
    }

    /**
     *
     * @param capacity
     * @return Priority based task manager
     */
    public static TaskManager getPriorityBasedTaskManager(final int capacity)
    {
        return new PriorityBasedTaskManager(capacity);
    }

    /**
     *
     * @return  Simple Task manager with default capacity of 10
     */
    public static TaskManager getDefaultTaskManager()
    {
        return getDefaultTaskManager(TaskManager.DEFAULT_CAPACITY);
    }

    /**
     *
     * @param capacity
     * @return Simple Task manager
     */
    public static TaskManager getDefaultTaskManager(final int capacity)
    {
        return new DefaultTaskManager(capacity);
    }

    /**
     *
     * @return Simple Task manager with default capacity of 10
     */
    public static TaskManager getFifoBasedTaskManager()
    {
        return getFifoBasedTaskManager(TaskManager.DEFAULT_CAPACITY);
    }

    /**
     *
     * @param capacity
     * @return FIFO based task manager
     */
    public static TaskManager getFifoBasedTaskManager(final int capacity)
    {
        return new FifoBasedTaskManager(capacity);
    }

}
