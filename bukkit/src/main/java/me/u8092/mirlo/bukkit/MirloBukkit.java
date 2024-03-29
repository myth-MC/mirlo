package me.u8092.mirlo.bukkit;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.bukkit.listeners.*;
import me.u8092.mirlo.common.boot.MirloBootstrap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public final class MirloBukkit extends MirloBootstrap<MirloBukkitPlugin> {
    public static MirloBukkit INSTANCE;
    private final LoggerWrapper logger = new LoggerWrapper() {
        @Override
        public void info(String message, Object... args) {
            getPlugin().getLogger().info(buildFullMessage(message, args));
        }

        @Override
        public void warn(String message, Object... args) {
            getPlugin().getLogger().warning(buildFullMessage(message, args));
        }

        @Override
        public void error(String message, Object... args) {
            getPlugin().getLogger().severe(buildFullMessage(message, args));
        }
    };

    public MirloBukkit(final @NotNull MirloBukkitPlugin plugin) {
        super(plugin, plugin.getDataFolder());
        INSTANCE = this;
    }

    public static byte[] byteArray(String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(message);

        return out.toByteArray();
    }

    @Override
    public void enable() {
        registerListeners();
    }

    @Override
    public void registerMirloChannel(MirloChannel channel) {
        getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(getPlugin(), "mirlo:" + channel.id());
        getPlugin().getServer().getMessenger().registerIncomingPluginChannel(getPlugin(), "mirlo:" + channel.id(), new PluginMessageReceiver());
    }

    @Override
    public void unregisterMirloChannel(MirloChannel channel) {
        getPlugin().getServer().getMessenger().unregisterOutgoingPluginChannel(getPlugin(), "mirlo:" + channel.id());
        getPlugin().getServer().getMessenger().unregisterIncomingPluginChannel(getPlugin(), "mirlo:" + channel.id());
    }

    @Override
    public void sendMirloMessage(MirloMessage message) {
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        player.sendPluginMessage(getPlugin(), "mirlo:" + message.channel(), byteArray(message.message()));
    }

    private void registerListeners() {
        // Bukkit listeners
        getPlugin().getServer().getPluginManager().registerEvents(new EntityDeathListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoinListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerGameModeChangeListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerLocaleChangeListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerExpChangeListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerToggleFlightListener(), getPlugin());

        // mirlo listeners
        Mirlo.get().getEventManager().registerListener(new MirloMessageListener());
    }
}
