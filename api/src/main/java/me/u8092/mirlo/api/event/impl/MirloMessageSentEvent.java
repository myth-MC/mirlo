package me.u8092.mirlo.api.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.u8092.mirlo.api.event.MirloEvent;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class MirloMessageSentEvent implements MirloEvent {
    private final String channel;
    private final String target;
    private final String message;
}
