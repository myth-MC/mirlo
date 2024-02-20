package me.u8092.mirlo.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.event.impl.MirloMessageSentEvent;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class MirloMessage {
    private final String channel;
    private final String target;
    private final String message;

    public void send() {
        Mirlo.get().sendMirloMessage(this);
        Mirlo.get().getEventManager().publish(new MirloMessageSentEvent(this));
    }
}
