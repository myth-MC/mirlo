package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;
import me.u8092.mirlo.api.event.impl.MirloMessageSentEvent;

public class MirloMessageSentListener implements MirloEventListener {
    @Override
    public void handle(final MirloEvent event) {
        if (event instanceof MirloMessageSentEvent messageSentEvent) {
            Mirlo.get().getLogger().info(messageSentEvent.message().channel() + " <- " + messageSentEvent.message().message());
        }
    }
}
