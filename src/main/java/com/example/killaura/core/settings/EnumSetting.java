package com.example.killaura.core.settings;

import java.util.Arrays;
import java.util.List;

/**
 * Настройка-перечисление для выбора режима.
 * Используется селектором в SettingsPanel (например, режим атаки KillAura).
 */
public class EnumSetting<E extends Enum<E>> extends Setting<E> {
    private final Class<E> enumClass;

    public EnumSetting(String name, String description, E defaultValue) {
        super(name, description, defaultValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    public Class<E> getEnumClass() {
        return enumClass;
    }

    public List<E> getValues() {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    /**
     * Циклически переключает на следующий режим.
     */
    public void cycle() {
        E[] constants = enumClass.getEnumConstants();
        int nextIndex = (getValue().ordinal() + 1) % constants.length;
        setValue(constants[nextIndex]);
    }

    /**
     * Циклически переключает на предыдущий режим.
     */
    public void cycleBack() {
        E[] constants = enumClass.getEnumConstants();
        int prevIndex = (getValue().ordinal() - 1 + constants.length) % constants.length;
        setValue(constants[prevIndex]);
    }

    @Override
    public String getType() {
        return "enum";
    }
}
