package com.example.killaura.core.settings;

/**
 * Логическая настройка (вкл/выкл).
 * Используется переключателем в SettingsPanel (throughWalls, onlyPlayers).
 */
public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, String description, boolean defaultValue) {
        super(name, description, defaultValue);
    }

    public boolean isEnabled() {
        return getValue();
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
