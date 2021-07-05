package me.farhan.managers;

import me.farhan.process.Priority;
import me.farhan.process.Process;
import me.farhan.taskmanager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultTaskManagerTest {

    private  TaskManager taskManager;

    @BeforeEach
    void init() {
       taskManager = TaskManagers.getDefaultTaskManager(3);
    }

    @Test
    public void addProcessTest() {
        taskManager.add(Process.of(Priority.LOW));
        assertEquals(1, taskManager.list().size());
    }

    @Test
    public void addProcessTestWithOverflow() {
        addToManager(3);
        long pid = taskManager.add(Process.of(Priority.HIGH));
        assertEquals(-1, pid);
    }

    @Test
    public void KillAllProcessTest() {
        addToManager(3);
        taskManager.killAll();
        assertEquals(0, taskManager.list().size());
    }

    @Test
    public void KillGroupTest() {
        addToManager(2);
        taskManager.add(Process.of(Priority.LOW));
        taskManager.killGroup(Priority.LOW);
        assertEquals(2, taskManager.list().size());
    }

    @Test
    public void activeProcessListTest() {
        addToManager(3);
        assertEquals(3, taskManager.list().size());
    }

    @Test
    public void killByPidTest() throws Exception
    {
        addToManager(2);
        long pid = taskManager.add(Process.of(Priority.HIGH));
        taskManager.kill(pid);
        assertEquals(2, taskManager.list().size());
    }

    private void addToManager(int max) {
        IntStream.iterate(0, i -> i + 1)
                .limit(max)
                .forEach(i -> taskManager.add(Process.newProcess()));
    }

}
