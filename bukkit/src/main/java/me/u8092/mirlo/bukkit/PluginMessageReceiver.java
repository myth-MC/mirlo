package me.u8092.mirlo.bukkit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.api.event.impl.MirloMessageReceiveEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class PluginMessageReceiver implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.startsWith("mirlo:")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        Mirlo.get().getEventManager().publish(new MirloMessageReceiveEvent(
                new MirloMessage(channel, in.readUTF())
        ));
    }
}
