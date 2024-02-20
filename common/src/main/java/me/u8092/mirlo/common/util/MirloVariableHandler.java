package me.u8092.mirlo.common.util;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.variable.MirloVariable;
import me.u8092.mirlo.api.variable.impl.MirloBooleanVariable;
import me.u8092.mirlo.api.variable.impl.MirloCountVariable;

public class MirloVariableHandler {
    public static void update(String event, String varOwner, boolean globalToo) {
        update(event, varOwner);
        if (globalToo) update(event, "global");
    }

    public static void update(String event, String varOwner) {
        for (MirloVariable<?> variable : Mirlo.get().getVariableManager().get(varOwner)) {
            if (variable instanceof MirloBooleanVariable booleanVariable) {
                for (String trueEvent : booleanVariable.trueEvents()) {
                    if (trueEvent.equals(event)) booleanVariable.value(true);
                }

                for (String falseEvent : booleanVariable.falseEvents()) {
                    if (falseEvent.equals(event)) booleanVariable.value(false);
                }

                for (String switchEvent : booleanVariable.switchEvents()) {
                    if (switchEvent.equals(event)) booleanVariable.value(!booleanVariable.value());
                }

                for (String resetEvent : booleanVariable.resetEvents()) {
                    if (resetEvent.equals(event)) booleanVariable.value(booleanVariable.defaultValue());
                }
            }

            if (variable instanceof MirloCountVariable countVariable) {
                for (String increaseEvent : countVariable.increaseEvents()) {
                    if (increaseEvent.equals(event)) countVariable.value(countVariable.value() + 1);
                }

                for (String decreaseEvent : countVariable.decreaseEvents()) {
                    if (decreaseEvent.equals(event)) countVariable.value(countVariable.value() - 1);
                }

                for (String resetEvent : countVariable.resetEvents()) {
                    if (resetEvent.equals(event)) countVariable.value(countVariable.defaultValue());
                }
            }
        }
    }
}
