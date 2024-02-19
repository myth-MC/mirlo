package me.u8092.mirlo.common.variables;

import java.util.List;

public class CountVariable extends Variable {
    private int value;
    private int defaultValue;
    private List<String> increaseEvents;
    private List<String> decreaseEvents;
    private List<String> resetEvents;

    public CountVariable(String name,
                         int value,
                         List<String> increaseEvents,
                         List<String> decreaseEvents,
                         List<String> resetEvents,
                         String owner) {
        super(name, owner);
        this.value = value;
        this.defaultValue = value;
        this.increaseEvents = increaseEvents;
        this.decreaseEvents = decreaseEvents;
        this.resetEvents = resetEvents;
    }

    public int getValue() { return value; }
    public int getDefaultValue() { return defaultValue; }
    public List<String> getIncreaseEvents() { return increaseEvents; }
    public List<String> getDecreaseEvents() { return decreaseEvents; }
    public List<String> getResetEvents() { return resetEvents; }
    public void setValue(int value) { this.value = value; }
}
