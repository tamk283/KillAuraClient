package com.example.killaura.core;

import com.example.killaura.core.settings.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс модуля.
 * Хранит имя, описание, категорию, состояние включения и список настроек.
 */
public abstract class BaseModule {
    private final String name;
    private final String description;
    private final Category category;
    private final int defaultKey;
    private boolean enabled = false;
    private final List<Setting<?>> settings = new ArrayList<>();

    protected BaseModule(String name, String description, Category category) {
        this(name, description, category, -1);
    }

    protected BaseModule(String name, String description, Category category, int defaultKey) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.defaultKey = defaultKey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getDefaultKey() {
        return defaultKey;
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

    /**
     * Регистрирует настройку модуля. Вызывается в конструкторе подкласса.
     */
    protected <S extends Setting<?>> S addSetting(S setting) {
        settings.add(setting);
        return setting;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public Setting<?> getSetting(String name) {
        for (Setting<?> setting : settings) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    /**
     * Категории модулей.
     */
    public enum Category {
        COMBAT, MOVEMENT, VISUALS, PLAYER, MISC
    }
}
