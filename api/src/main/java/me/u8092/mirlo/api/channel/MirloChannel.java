package me.u8092.mirlo.api.channel;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
public abstract class MirloChannel {
    private final String id;
    private final List<String> send;
    private final List<String> receive;

    public MirloChannel(String id,
                        List<String> send,
                        List<String> receive) {
        this.id = id;
        this.send = send;
        this.receive = receive;
    }
}
