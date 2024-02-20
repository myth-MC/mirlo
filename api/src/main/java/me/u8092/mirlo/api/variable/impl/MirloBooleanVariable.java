package me.u8092.mirlo.api.variable.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.variable.MirloVariable;

import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
public final class MirloBooleanVariable extends MirloVariable<Boolean> {
    private final List<String> trueEvents;
    private final List<String> falseEvents;
    private final List<String> switchEvents;
    private final List<String> resetEvents;

    public MirloBooleanVariable(String name,
                                String owner,
                                boolean defaultValue,
                                List<String> trueEvents,
                                List<String> falseEvents,
                                List<String> switchEvents,
                                List<String> resetEvents) {
        super(name, owner, defaultValue);
        this.trueEvents = trueEvents;
        this.falseEvents = falseEvents;
        this.switchEvents = switchEvents;
        this.resetEvents = resetEvents;
    }
}
