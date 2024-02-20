package me.u8092.mirlo.api.channel.impl;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.channel.MirloChannel;

import java.util.List;

@Getter
@Accessors(fluent = true)
public final class BasicMirloChannel extends MirloChannel {
    private final List<String> send;
    private final List<String> receive;

    public BasicMirloChannel(String id,
                             List<String> send,
                             List<String> receive) {
        super(id);
        this.send = send;
        this.receive = receive;
    }
}