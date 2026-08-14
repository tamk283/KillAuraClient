package com.example.killaura.gui.widgets;

import com.example.killaura.core.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Слайдер для числовой настройки.
 */
public class SliderWidget {
    private final NumberSetting setting;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean dragging = false;

    public SliderWidget(NumberSetting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);

        // Трек (фон)
        context.fill(x, y + height / 2 - 3, x + width, y + height / 2 + 3, 0xFF2a2a3e);

        double ratio = (setting.getDouble() - setting.getMin()) / (setting.getMax() - setting.getMin());
        int filledWidth = (int) (width * ratio);

        // Заполненная часть
        int fillColor = dragging ? 0xFF00d4ff : (hovered ? 0xFF0099cc : 0xFF0f3460);
        context.fill(x, y + height / 2 - 3, x + filledWidth, y + height / 2 + 3, fillColor);

        // Ползунок
        int handleX = x + filledWidth - 4;
        context.fill(handleX, y, handleX + 8, y + height, 0xFFFFFFFF);

        // Подпись и значение
        MinecraftClient client = MinecraftClient.getInstance();
        context.drawTextWithShadow(
            client.textRenderer,
            Text.of(setting.getName()),
            x,
            y - 12,
            0xFFCCCCCC
        );
        context.drawTextWithShadow(
            client.textRenderer,
            Text.of(formatValue(setting.getDouble())),
            x + width - client.textRenderer.getWidth(formatValue(setting.getDouble())),
            y - 12,
            0xFFFFFFFF
        );
    }

    private String formatValue(double value) {
        if (setting.getStep() >= 1) {
            return String.valueOf((int) value);
        }
        return String.format("%.1f", value);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isHovered(mouseX, mouseY)) {
            dragging = true;
            updateValue(mouseX);
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            dragging = false;
            return true;
        }
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (dragging) {
            updateValue(mouseX);
            return true;
        }
        return false;
    }

    private void updateValue(double mouseX) {
        double ratio = (mouseX - x) / width;
        ratio = Math.max(0, Math.min(1, ratio));
        double newValue = setting.getMin() + ratio * (setting.getMax() - setting.getMin());
        setting.setValue(newValue);
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x - 4 && mouseX <= x + width + 4 && mouseY >= y - 4 && mouseY <= y + height + 4;
    }

    public NumberSetting getSetting() {
        return setting;
    }
}
