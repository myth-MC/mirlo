package me.u8092.mirlo.commons.variables;

import java.util.List;

public class CountVariable {
    private String name;
    private int value;
    private int defaultValue;
    private List<String> increaseEvents;
    private List<String> decreaseEvents;
    private List<String> resetEvents;
    private String owner;

    public CountVariable(String name,
                         int value,
                         List<String> increaseEvents,
                         List<String> decreaseEvents,
                         List<String> resetEvents,
                         String owner) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
        this.increaseEvents = increaseEvents;
        this.decreaseEvents = decreaseEvents;
        this.resetEvents = resetEvents;
        this.owner = owner;
    }

    public String getName() { return name; }
    public int getValue() { return value; }
    public int getDefaultValue() { return defaultValue; }
    public List<String> getIncreaseEvents() { return increaseEvents; }
    public List<String> getDecreaseEvents() { return decreaseEvents; }
    public List<String> getResetEvents() { return resetEvents; }
    public String getOwner() { return owner; }
    public void setValue(int value) { this.value = value; }
}
