package com.example.killaura.core;

public abstract class BaseModule {
    private final String name;
    private final String description;
    private boolean enabled = false;

    public BaseModule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
        onEnable();
    }

    public void disable() {
        enabled = false;
        onDisable();
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