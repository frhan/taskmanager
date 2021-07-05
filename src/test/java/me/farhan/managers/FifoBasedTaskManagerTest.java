package me.farhan.managers;

import me.farhan.process.Process;
import me.farhan.taskmanager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifoBasedTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void init() {
        taskManager = TaskManagers.getFifoBasedTaskManager(3);
    }

    @Test
    public void testAddProcess()
    {
        addToManager(4);
        assertEquals(3, taskManager.list().size());
    }

    @Test
    public void testAddProcessWithQueue()
    {
        addToManager(3);
        long pid = taskManager.add(Process.newProcess());
        assertEquals(pid, taskManager.list()
                                     .stream()
                                     .filter(p -> p.getPid() == pid)
                                     .findFirst()
                                     .get()
                                     .getPid());
    }

    @Test
    public void testKillAll() {
        addToManager(3);
        taskManager.killAll();
        assertEquals(0,taskManager.list().size());
    }


    private void addToManager(int max) {
        IntStream.iterate(0, i -> i + 1)
                .limit(max)
                .forEach(i -> taskManager.add(Process.newProcess()));
    }


}
