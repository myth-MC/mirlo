package me.u8092.mirlo.api.variable.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.variable.MirloVariable;

import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
public final class MirloCountVariable extends MirloVariable<Integer> {
    private final List<String> increaseEvents;
    private final List<String> decreaseEvents;
    private final List<String> resetEvents;

    public MirloCountVariable(String name,
                              String owner,
                              Integer defaultValue,
                              List<String> increaseEvents,
                              List<String> decreaseEvents,
                              List<String> resetEvents) {
        super(name, owner, defaultValue);
        this.increaseEvents = increaseEvents;
        this.decreaseEvents = decreaseEvents;
        this.resetEvents = resetEvents;
    }
}