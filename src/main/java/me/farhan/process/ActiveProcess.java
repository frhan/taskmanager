package me.farhan.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ActiveProcess extends Process
{
    public ActiveProcess(final long pid, Priority priority)
    {
        super(priority,pid, LocalDateTime.now());
    }

}
