package me.u8092.watchdog.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ExampleEvent extends Event {
    // dummy event
    // will be used soon
    private static final HandlerList handlerList = new HandlerList();
    private final String example;
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public ExampleEvent(String example) {
        this.example = example;
    }

    public String getExample() {
        return example;
    }
}
