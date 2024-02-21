package me.u8092.mirlo.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.impl.MirloMessageSendEvent;

@Getter()
@Accessors(fluent = true)
public final class MirloMessage extends Message {
    public MirloMessage(String channel, String message) {
        super(channel, message);
    }

    public void send() {
        Mirlo.get().sendMirloMessage(this);
        Mirlo.get().getEventManager().publish(new MirloMessageSendEvent(this));
    }
}
