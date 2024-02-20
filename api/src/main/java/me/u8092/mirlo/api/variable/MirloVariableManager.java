package me.u8092.mirlo.api.variable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.variable.impl.MirloBooleanVariable;
import me.u8092.mirlo.api.variable.impl.MirloCountVariable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MirloVariableManager {
    public static final MirloVariableManager INSTANCE = new MirloVariableManager();
    private static final List<MirloVariable<?>> VARIABLES = new ArrayList<>();

    public void add(final @NotNull MirloVariable<?> variable) {
        VARIABLES.add(variable);
    }

    public void remove(final @NotNull MirloVariable<?> variable) {
        VARIABLES.remove(variable);
    }

    public List<MirloVariable<?>> get() {
        return VARIABLES;
    }

    public List<MirloVariable<?>> get(final @NotNull String owner) {
        List<MirloVariable<?>> toSend = new ArrayList<>();

        for (MirloVariable<?> variable : VARIABLES) {
            if (variable.owner().equals(owner)) toSend.add(variable);
        }

        return toSend;
    }

    @ApiStatus.Internal
    public void clear() {
        VARIABLES.clear();
    }

    public void initialize(String scope, String owner) {
        ConfigurationSection section = Mirlo.get().getConfig().getVariables().getSection();
        for (String variable : section.getKeys(false)) {
            if (section.getString(variable + ".scope").equals(scope)) {
                if (section.getString(variable + ".type").equals("count")) {
                    add(new MirloCountVariable(
                            variable,
                            owner,
                            section.getInt(variable + ".default"),
                            section.getStringList(variable + ".increase"),
                            section.getStringList(variable + ".decrease"),
                            section.getStringList(variable + ".reset")
                    ));

                    if (Mirlo.get().getConfig().getSettings().isDebug())
                        Mirlo.get().getLogger().info("Registered new CountVariable '" + variable + "' (" + owner + ")");
                }

                if (section.getString(variable + ".type").equals("boolean")) {
                    add(new MirloBooleanVariable(
                            variable,
                            owner,
                            section.getBoolean(variable + ".default"),
                            section.getStringList(variable + ".true"),
                            section.getStringList(variable + ".false"),
                            section.getStringList(variable + ".switch"),
                            section.getStringList(variable + ".reset")
                    ));

                    if (Mirlo.get().getConfig().getSettings().isDebug())
                        Mirlo.get().getLogger().info("Registered new BooleanVariable '" + variable + "' (" + owner + ")");
                }
            }
        }
    }
}
