package me.farhan.process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActiveProcessTest {

    private Process process;

    @BeforeEach
    void init() {
       process = new ActiveProcess(1, Priority.LOW);
    }

    @Test
    public void processCreationTestWithPriority() {
        assertEquals(Priority.LOW, process.getPriority());
    }

    @Test
    public void processCreationTestWithPid() {
        assertEquals(1, process.getPid());
    }

    @Test
    public void processCreationTestWithCreationDate() {
        assertNotNull(process.getCreationDateTime());
    }
}
