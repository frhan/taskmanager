package me.farhan.process;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
public class Process implements Comparable<Process>
{
    @NonNull
    private final Priority priority;
    private final long pid;
    private final LocalDateTime creationDateTime;

    Process(final Priority priority, long pid, LocalDateTime creationDateTime) {
        this.pid = pid;
        this.priority = priority;
        this.creationDateTime = creationDateTime;
    }

    /**
     *
     * @param priority
     * @return new Process instance
     */
    public static Process of(final Priority priority) {
        return new Process(priority, -1, null);
    }

    /**
     *
     * @return new Process instance
     */
    public static Process newProcess() {
        return new Process(Priority.MEDIUM, -1, null);
    }

    @Override
    public int compareTo(Process process) {
        return process.getPriority().getValue() - this.getPriority().getValue();
    }

    /**
     * kill the process
     */
    public void kill() {
      System.out.println("Killing process: "+ this);
    }

    @Override
    public String toString() {
        return "Process{" +
                " pid='" + pid + '\'' +
                ", priority='" + priority + '\'' +
                ", creationDateTime=" + creationDateTime +
                '}';
    }

    public static Comparator<Process> processPriorityComparator()
    {
        return Comparator.comparingInt(p -> p.getPriority().getValue());
    }

    public static Comparator<Process> processCreationTimeComparator()
    {
        return Comparator.comparing(Process::getCreationDateTime);
    }
}
