package com.example.killaura.core.settings;

/**
 * Числовая настройка с минимумом, максимумом и шагом.
 * Используется слайдером в SettingsPanel (range, delay, rotationSpeed).
 */
public class NumberSetting extends Setting<Double> {
    private final double min;
    private final double max;
    private final double step;

    public NumberSetting(String name, String description, double defaultValue, double min, double max, double step) {
        super(name, description, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    @Override
    public void setValue(Double value) {
        // Ограничиваем диапазон
        double clamped = Math.max(min, Math.min(max, value));
        // Округляем до шага
        if (step > 0) {
            clamped = Math.round(clamped / step) * step;
        }
        super.setValue(clamped);
    }

    public double getDouble() {
        return getValue();
    }

    public float getFloat() {
        return getValue().floatValue();
    }

    public int getInt() {
        return (int) Math.round(getValue());
    }

    public long getLong() {
        return Math.round(getValue());
    }

    @Override
    public String getType() {
        return "number";
    }
}
