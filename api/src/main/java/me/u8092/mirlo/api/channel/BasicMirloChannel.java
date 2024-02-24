package me.u8092.mirlo.api.channel;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.impl.MirloChannelRegisterEvent;
import me.u8092.mirlo.api.event.impl.MirloChannelUnregisterEvent;

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

    public void register() {
        Mirlo.get().getChannelManager().add(this);
        Mirlo.get().registerMirloChannel(this);

        Mirlo.get().getEventManager().publish(new MirloChannelRegisterEvent(this));
    }

    public void unregister() {
        Mirlo.get().getChannelManager().remove(this);
        Mirlo.get().unregisterMirloChannel(this);

        Mirlo.get().getEventManager().publish(new MirloChannelUnregisterEvent(this));
    }
}
