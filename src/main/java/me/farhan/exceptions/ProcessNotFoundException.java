package me.farhan.exceptions;

public class ProcessNotFoundException extends Exception {
    public ProcessNotFoundException(long pid) {
        super("Process not found with pid: "+pid);
    }
}
