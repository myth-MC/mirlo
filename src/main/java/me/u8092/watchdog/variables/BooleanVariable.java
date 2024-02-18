package me.u8092.watchdog.variables;

public class BooleanVariable {
    private String name;
    private boolean value;
    private boolean defaultValue;
    private String owner;

    public BooleanVariable(String name, boolean value, String owner) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
        this.owner = owner;
    }

    public String getName() { return name; }
    public boolean getValue() { return value; }
    public boolean getDefaultValue() { return defaultValue; }
    public void setValue(boolean value) { this.value = value; }
    public String getOwner() { return owner; }
}
