package me.u8092.mirlo.api.variable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class MirloVariable<T> {
    private final String name;
    private final String owner;
    private final T defaultValue;
    @Setter
    private T value;

    public MirloVariable(String name,
                         String owner,
                         T defaultValue) {
        this.name = name;
        this.owner = owner;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
}
