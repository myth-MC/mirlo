package me.u8092.mirlo.api;

import me.u8092.mirlo.api.event.MirloEventManager;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import org.jetbrains.annotations.NotNull;

public interface Mirlo {
    @NotNull LoggerWrapper getLogger();
    @NotNull
    default MirloEventManager getEventManager() {
        return MirloEventManager.INSTANCE;
    }

    @NotNull
    static Mirlo get() {
        return MirloSupplier.get();
    }
}
