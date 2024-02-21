package me.u8092.mirlo.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.impl.MirloMessageReceiveEvent;
import me.u8092.mirlo.api.message.MirloMessage;

public class PluginMessageReceiver {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if(!(event.getSource() instanceof ServerConnection)) return;

        if (!event.getIdentifier().getId().startsWith("mirlo:")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        Mirlo.get().getEventManager().publish(new MirloMessageReceiveEvent(
                new MirloMessage(event.getIdentifier().getId(), in.readUTF())
        ));
    }
}
