package me.u8092.mirlo.api;

import me.u8092.mirlo.api.config.MirloConfiguration;
import me.u8092.mirlo.api.event.MirloEventManager;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import org.jetbrains.annotations.NotNull;

public interface Mirlo {
    void reload();

    @NotNull LoggerWrapper getLogger();

    @NotNull MirloConfiguration getConfig();

    @NotNull
    default MirloEventManager getEventManager() {
        return MirloEventManager.INSTANCE;
    }

    @NotNull
    static Mirlo get() {
        return MirloSupplier.get();
    }
}
