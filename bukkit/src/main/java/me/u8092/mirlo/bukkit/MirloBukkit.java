package me.u8092.mirlo.bukkit;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.MirloMessage;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.bukkit.listeners.EntityDamageByEntityListener;
import me.u8092.mirlo.bukkit.listeners.EntityDeathListener;
import me.u8092.mirlo.bukkit.listeners.MirloMessageSentListener;
import me.u8092.mirlo.bukkit.listeners.PlayerJoinListener;
import me.u8092.mirlo.common.boot.MirloBootstrap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    public static byte[] byteArray(List<String> bytes) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String b : bytes) {
            out.writeUTF(b);
        }

        return out.toByteArray();
    }

    public static byte[] byteArray(String bytesSeparatedByComma) {
        List<String> bytes = List.of(bytesSeparatedByComma.split(","));

        return byteArray(bytes);
    }

    @Override
    public void enable() {
        registerListeners();
        registerCommands();
    }

    @Override
    public void registerChannels(List<MirloChannel> channels) {
        for (MirloChannel channel : channels) {
            getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(getPlugin(), "mirlo:" + channel.id());
        }
    }

    @Override
    public void sendMirloMessage(MirloMessage message) {
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(getPlugin(), "mirlo:" + message.channel(), byteArray(message.message()));
    }

    private void registerListeners() {
        // Bukkit listeners
        getPlugin().getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new EntityDeathListener(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoinListener(), getPlugin());

        // Mirlo listeners
        Mirlo.get().getEventManager().registerListener(new MirloMessageSentListener());
    }

    private void registerCommands() {
        //INSTANCE.getPlugin().getCommand("mirloreload").setExecutor(new ReloadCommand());
    }
}
