package me.u8092.mirlo.api.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.message.Message;
import me.u8092.mirlo.api.event.MirloEvent;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class MirloMessageReceiveEvent implements MirloEvent {
    private final Message message;
}
