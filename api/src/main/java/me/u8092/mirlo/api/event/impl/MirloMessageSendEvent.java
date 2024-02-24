package me.u8092.mirlo.api.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.message.Message;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class MirloMessageSendEvent implements MirloEvent {
    private final Message message;
}
