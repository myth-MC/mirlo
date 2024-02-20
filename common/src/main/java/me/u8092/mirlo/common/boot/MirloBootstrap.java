package me.u8092.mirlo.common.boot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.MirloMessage;
import me.u8092.mirlo.api.MirloSupplier;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.config.MirloConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class MirloBootstrap<T> implements Mirlo {
    private T plugin;
    private MirloConfiguration config;

    public MirloBootstrap(final @NotNull T plugin,
                          final File dataDirectory) {
        // Set the Mirlo API
        MirloSupplier.set(this);

        this.plugin = plugin;
        this.config = new MirloConfiguration(dataDirectory);
    }

    public final void initialize() {
        getLogger().info("Enabling all tasks and features...");

        reload();

        try {
            enable();

            getLogger().info("Done!");
        } catch (Throwable throwable) {
            getLogger().error("An error has occurred while initializing mirlo: {}", throwable);
            throwable.printStackTrace(System.err);
            return;
        }

        // Register channels
        registerChannels(Mirlo.get().getChannelManager().get());

        // reserved for update checking
    }

    public abstract void enable();

    public abstract void registerChannels(List<MirloChannel> channels);

    public abstract void sendMirloMessage(MirloMessage message);

    public void shutdown() {
        getLogger().info("Shutting down...");

        // ...

        getLogger().info("Successfully shut doiwn.");
    }

    public final void reload() {
        getConfig().load();
        getLogger().warn("Stored variables have been resetted to their default values.");
    }
}
