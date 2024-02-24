package me.u8092.mirlo.minestom;

import lombok.Getter;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.boot.MirloBootstrap;
import me.u8092.mirlo.minestom.listeners.*;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
public class MirloMinestom extends MirloBootstrap<MirloMinestomExtension> {
    public static MirloMinestom INSTANCE;

    private final LoggerWrapper logger = new LoggerWrapper() {
        @Override
        public void info(String message, Object... args) {
            getPlugin().getLogger().info(buildFullMessage(message, args));
        }

        @Override
        public void warn(String message, Object... args) {
            getPlugin().getLogger().warn(buildFullMessage(message, args));
        }

        @Override
        public void error(String message, Object... args) {
            getPlugin().getLogger().error(buildFullMessage(message, args));
        }
    };

    public MirloMinestom(final @NotNull MirloMinestomExtension extension) {
        super(extension, extension.getDataDirectory().toFile());
        INSTANCE = this;
    }

    @Override
    public void enable() {
        registerListeners();
    }

    @Override
    public void registerMirloChannel(MirloChannel channel) {
        // There's no need to register channels in Minestom
    }

    @Override
    public void unregisterMirloChannel(MirloChannel channel) {
        // There's no need to unregister channels in Minestom
    }

    @Override
    public void sendMirloMessage(MirloMessage message) {
        Optional<@NotNull Player> player = MinecraftServer.getConnectionManager().getOnlinePlayers().stream().findFirst();
        player.ifPresent(p -> p.sendPluginMessage("mirlo:" + message.channel(), message.message()));
    }

    private void registerListeners() {
        // Minestom listeners
        EntityDeathListener.register(MinecraftServer.getGlobalEventHandler());
        PickupExperienceListener.register(MinecraftServer.getGlobalEventHandler());
        PlayerGameModeChangeListener.register(MinecraftServer.getGlobalEventHandler());
        PlayerSpawnListener.register(MinecraftServer.getGlobalEventHandler());
        PlayerStartFlyingListener.register(MinecraftServer.getGlobalEventHandler());
        PlayerStopFlyingListener.register(MinecraftServer.getGlobalEventHandler());

        // mirlo listeners
        Mirlo.get().getEventManager().registerListener(new MirloMessageListener());
    }
}
