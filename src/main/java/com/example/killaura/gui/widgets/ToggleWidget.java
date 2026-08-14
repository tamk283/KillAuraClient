package com.example.killaura.gui.widgets;

import com.example.killaura.core.settings.BooleanSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Переключатель для логической настройки.
 */
public class ToggleWidget {
    private final BooleanSetting setting;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private static final int TOGGLE_W = 30;
    private static final int TOGGLE_H = 14;

    public ToggleWidget(BooleanSetting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);
        boolean enabled = setting.isEnabled();

        // Подпись
        context.drawTextWithShadow(
            net.minecraft.client.MinecraftClient.getInstance().textRenderer,
            Text.of(setting.getName()),
            x,
            y + 1,
            0xFFCCCCCC
        );

        // Фон переключателя
        int toggleX = x + width - TOGGLE_W;
        int toggleY = y;
        int bgColor = enabled ? 0xFF0f6040 : (hovered ? 0xFF2a2a3e : 0xFF1a1a2e);
        context.fill(toggleX, toggleY, toggleX + TOGGLE_W, toggleY + TOGGLE_H, bgColor);

        // Ручка переключателя
        int handleSize = TOGGLE_H - 4;
        int handleX = enabled ? toggleX + TOGGLE_W - handleSize - 2 : toggleX + 2;
        int handleY = toggleY + 2;
        context.fill(handleX, handleY, handleX + handleSize, handleY + handleSize, 0xFFFFFFFF);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isHovered(mouseX, mouseY)) {
            setting.setValue(!setting.getValue());
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        int toggleX = x + width - TOGGLE_W;
        return mouseX >= toggleX && mouseX <= toggleX + TOGGLE_W && mouseY >= y && mouseY <= y + TOGGLE_H;
    }

    public BooleanSetting getSetting() {
        return setting;
    }
}
