package com.example.killaura.core;

import java.util.Locale;

public abstract class BaseModule {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean enabled = false;

    public BaseModule(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getConfigKey() {
        return "module." + name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_") + ".enabled";
    }

    public void enable() {
        setEnabled(true, true);
    }

    public void disable() {
        setEnabled(false, true);
    }

    void restoreEnabledState(boolean enabled) {
        setEnabled(enabled, false);
    }

    private void setEnabled(boolean enabled, boolean persist) {
        if (this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }

        if (persist) {
            ConfigManager.getInstance().setBoolean(getConfigKey(), enabled);
        }
    }

    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public void update() {
        if (enabled) {
            onUpdate();
        }
    }

    protected void onEnable() {}
    protected void onDisable() {}
    protected void onUpdate() {}
}
