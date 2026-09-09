package com.example.killaura.core;

/**
 * Categories used to group modules in the Click GUI.
 */
public enum ModuleCategory {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    VISUALS("Visuals"),
    PLAYER("Player"),
    MISCELLANEOUS("Misc");

    private final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
