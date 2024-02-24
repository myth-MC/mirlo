package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;
import me.u8092.mirlo.api.event.impl.MirloMessageReceiveEvent;
import me.u8092.mirlo.api.event.impl.MirloMessageSendEvent;

public class MirloMessageListener implements MirloEventListener {
    @Override
    public void handle(final MirloEvent event) {
        if (event instanceof MirloMessageSendEvent messageSendEvent) {
            if(Mirlo.get().getConfig().getSettings().isDebug())
                Mirlo.get().getLogger().info("[outgoing] " + messageSendEvent.message().message() + " to " + messageSendEvent.message().channel());
        }

        if (event instanceof MirloMessageReceiveEvent messageReceiveEvent) {
            if(Mirlo.get().getConfig().getSettings().isDebug())
                Mirlo.get().getLogger().info("[incoming] " + messageReceiveEvent.message().message() + " from " + messageReceiveEvent.message().channel());
        }
    }
}
