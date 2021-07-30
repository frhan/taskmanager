package me.farhan.taskmanager;

import me.farhan.managers.TaskManagers;
import me.farhan.process.Process;

public class TaskManagerApplication {

    public static void main(String [] args)
    {
        TaskManager taskManager = TaskManagers.getDefaultTaskManager();
        taskManager.add(Process.newProcess());
        taskManager.add(Process.newProcess());
    }
}
