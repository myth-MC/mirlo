package me.u8092.mirlo.velocity;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.boot.MirloBootstrap;
import me.u8092.mirlo.velocity.listeners.MirloMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public final class MirloVelocity extends MirloBootstrap<MirloVelocityPlugin> {
    public static MirloVelocity INSTANCE;

    public MirloVelocity(final @NotNull MirloVelocityPlugin plugin) {
        super(plugin, plugin.getDataDirectory().toFile());
        INSTANCE = this;
    }

    private final LoggerWrapper logger = new LoggerWrapper() {
        @Override
        public void info(final String message, final Object... args) {
            getPlugin().getLogger().info(message, args);
        }

        @Override
        public void warn(final String message, final Object... args) {
            getPlugin().getLogger().warn(message, args);
        }

        @Override
        public void error(final String message, final Object... args) {
            getPlugin().getLogger().error(message, args);
        }
    };

    @Override
    public void enable() {
        registerListeners();
    }

    private void registerListeners() {
        // Velocity listeners
        getPlugin().getServer().getEventManager().register(getPlugin(), new PluginMessageReceiver());

        // mirlo listeners
        Mirlo.get().getEventManager().registerListener(new MirloMessageListener());
    }

    @Override
    public void registerChannels(List<MirloChannel> channels) {
        for (MirloChannel channel : channels) {
            getPlugin().getServer().getChannelRegistrar().register(MinecraftChannelIdentifier.from("mirlo:" + channel.id()));
        }
    }

    public static byte[] byteArray(String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(message);

        return out.toByteArray();
    }

    @Override
    public void sendMirloMessage(MirloMessage message) {
        for(RegisteredServer server : getPlugin().getServer().getAllServers()) {
            server.sendPluginMessage(MinecraftChannelIdentifier.from("mirlo:" + message.channel()), byteArray(message.message()));
        }
    }
}
