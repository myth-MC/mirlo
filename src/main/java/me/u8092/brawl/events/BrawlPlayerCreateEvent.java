package me.u8092.brawl.events;

import me.u8092.brawl.match.BrawlPlayer;
import me.u8092.brawl.match.BrawlPlayerHandler;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BrawlPlayerCreateEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final BrawlPlayer brawlPlayer;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public BrawlPlayerCreateEvent(BrawlPlayer brawlPlayer) {
        this.brawlPlayer = brawlPlayer;
    }

    public BrawlPlayer getBrawlPlayer() {
        return brawlPlayer;
    }
}
