package me.farhan.process;

public enum Priority {
    LOW(0),
    MEDIUM(2),
    HIGH(5);

    private final int value;

    Priority(int value){
        this.value = value;
    }

    public int getValue() {
        return  this.value;
    }
}
