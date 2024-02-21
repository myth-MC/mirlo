package me.u8092.mirlo.api.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MirloEventManager {
    public static final MirloEventManager INSTANCE = new MirloEventManager();
    private static final Collection<MirloEventListener> EVENT_LISTENERS = new Vector<>(0);
    private static final ExecutorService EVENT_SERVICE = Executors.newSingleThreadExecutor();

    @ApiStatus.Internal
    public void publish(final @NotNull MirloEvent event) {
        if (EVENT_LISTENERS.isEmpty()) return;

        EVENT_SERVICE.execute(() -> {
            for (final MirloEventListener eventListener : EVENT_LISTENERS) {
                try {
                    eventListener.handle(event);
                } catch (Throwable throwable) {
                    Mirlo.get().getLogger().error("Could not pass {} to listener: {}",
                            event.getClass().getSimpleName(), throwable);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    public synchronized void registerListener(final @NotNull MirloEventListener... listeners) {
        EVENT_LISTENERS.addAll(Arrays.asList(listeners));
    }

    @SuppressWarnings("unused")
    public synchronized void unregisterListener(final @NotNull MirloEventListener... listeners) {
        EVENT_LISTENERS.removeAll(Arrays.asList(listeners));
    }
}
