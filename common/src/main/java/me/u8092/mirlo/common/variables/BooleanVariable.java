package me.u8092.mirlo.common.variables;

import java.util.List;

public class BooleanVariable extends Variable {
    private boolean value;
    private boolean defaultValue;
    private List<String> trueEvents;
    private List<String> falseEvents;
    private List<String> switchEvents;
    private List<String> resetEvents;

    public BooleanVariable(String name,
                           boolean value,
                           List<String> trueEvents,
                           List<String> falseEvents,
                           List<String> switchEvents,
                           List<String> resetEvents,
                           String owner) {
        super(name, owner);
        this.value = value;
        this.defaultValue = value;
        this.trueEvents = trueEvents;
        this.falseEvents = falseEvents;
        this.switchEvents = switchEvents;
        this.resetEvents = resetEvents;
    }

    public boolean getValue() { return value; }
    public boolean getDefaultValue() { return defaultValue; }
    public void setValue(boolean value) { this.value = value; }
    public List<String> getTrueEvents() { return trueEvents; }
    public List<String> getFalseEvents() { return falseEvents; }
    public List<String> getSwitchEvents() { return switchEvents; }
    public List<String> getResetEvents() { return resetEvents; }
}
