package me.u8092.mirlo.api.channel;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class MirloChannel {
    private final String id;

    public MirloChannel(String id) {
        this.id = id;
    }
}
