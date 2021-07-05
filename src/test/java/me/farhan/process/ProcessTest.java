package me.farhan.process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessTest {

    private Process process;

    @BeforeEach
    void init() {
       process = Process.of(Priority.LOW);
    }

    @Test
    public void processCreationTestWithPid() {
        assertEquals(-1, process.getPid());
    }

    @Test
    public void processCreationTestWithCreationDate() {
        assertEquals(null, process.getCreationDateTime());
    }
}
