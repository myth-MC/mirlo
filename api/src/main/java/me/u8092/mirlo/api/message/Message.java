package me.u8092.mirlo.api.message;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class Message {
    String channel;
    String message;

    public Message(String channel,
                   String message) {
        this.channel = channel;
        this.message = message;
    }
}
