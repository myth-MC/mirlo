package me.u8092.mirlo.common.variables;

public class Variable {
    String name;
    private String owner;

    public Variable(String name,
                    String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() { return name; }
    public String getOwner() { return owner; }
}
