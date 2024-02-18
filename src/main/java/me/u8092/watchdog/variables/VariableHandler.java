package me.u8092.watchdog.variables;

import java.util.ArrayList;
import java.util.List;

public class VariableHandler {
    private static List<IntVariable> intVariables = new ArrayList<>();
    private static List<BooleanVariable> booleanVariables = new ArrayList<>();

    public static List<BooleanVariable> getBooleanVariables() {
        return booleanVariables;
    }

    public static List<IntVariable> getIntVariables() {
        return intVariables;
    }

    public static BooleanVariable getBooleanVariable(String owner, String name) {
        for(BooleanVariable booleanVariable : booleanVariables) {
            if(booleanVariable.getOwner().equals(owner) && booleanVariable.getName().equals(name)) return booleanVariable;
        }

        return null;
    }

    public static IntVariable getIntVariable(String owner, String name) {
        for(IntVariable intVariable : intVariables) {
            if(intVariable.getOwner().equals(owner) && intVariable.getName().equals(name)) return intVariable;
        }

        return null;
    }

    public static void addBooleanVariable(BooleanVariable booleanVariable) {
        booleanVariables.add(booleanVariable);
    }

    public static void addIntVariable(IntVariable intVariable) {
        intVariables.add(intVariable);
    }
}
