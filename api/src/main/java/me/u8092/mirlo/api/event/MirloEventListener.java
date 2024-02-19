package me.u8092.mirlo.api.event;

@FunctionalInterface
public interface MirloEventListener {
    void handle(final MirloEvent event);
}
