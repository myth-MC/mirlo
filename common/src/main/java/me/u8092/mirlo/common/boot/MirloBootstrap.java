package me.u8092.mirlo.common.boot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.MirloSupplier;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public abstract class MirloBootstrap<T> implements Mirlo {
    private T plugin;

    public MirloBootstrap(final @NotNull T plugin) {
        // Set the Mirlo API
        MirloSupplier.set(this);

        this.plugin = plugin;
    }

    public final void initialize() {
        getLogger().info("Enabling all tasks and features...");

        try {
            enable();

            getLogger().info("Done!");
        } catch(Throwable throwable) {
            getLogger().error("An error has occurred while launching Mirlo: {}", throwable);
            throwable.printStackTrace(System.err);
            return;
        }

        // reserved for update checking
    }

    public abstract void enable();

    public void shutdown() {
        getLogger().info("Shutting down...");

        // ...

        getLogger().info("Successfully shut doiwn.");
    }
}
