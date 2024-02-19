package me.u8092.mirlo.api;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@UtilityClass
public class MirloSupplier {
    private Mirlo mirlo;

    public void set(final @NotNull Mirlo m) {
        if(mirlo != null) {
            throw new AlreadyInitializedException();
        }
        mirlo = Objects.requireNonNull(m);
    }

    public @NotNull Mirlo get() {
        return mirlo;
    }
}
