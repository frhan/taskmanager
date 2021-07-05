package me.farhan.managers;

import me.farhan.process.Priority;
import me.farhan.process.Process;
import me.farhan.taskmanager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityBasedTaskManagerTest
{
    private TaskManager taskManager;

    @BeforeEach
    void init() {
        taskManager = TaskManagers.getPriorityBasedTaskManager(3);
    }

    @Test
    public void testAddProcessNormal(){
        taskManager.add(Process.of(Priority.LOW));
        taskManager.add(Process.of(Priority.HIGH));
        taskManager.add(Process.of(Priority.HIGH));
        taskManager.add(Process.of(Priority.MEDIUM));
        assertEquals(3, taskManager.list().size());
    }

    @Test
    public void testAddProcessWithSameOverflow(){
        taskManager.add(Process.of(Priority.LOW));
        taskManager.add(Process.of(Priority.HIGH));
        taskManager.add(Process.of(Priority.HIGH));
        long pid = taskManager.add(Process.of(Priority.LOW));
        assertEquals(-1, pid);
    }

    @Test
    public void testAddProcessWithSamePriorityAndLowestTimestamp(){
        long pid = taskManager.add(Process.of(Priority.LOW));
        taskManager.add(Process.of(Priority.LOW));
        taskManager.add(Process.of(Priority.HIGH));
        taskManager.add(Process.of(Priority.MEDIUM));
        assertEquals(false, taskManager.list()
                                        .stream()
                                        .filter(p -> p.getPid() == pid)
                                        .findFirst()
                                        .isPresent());
    }


}
