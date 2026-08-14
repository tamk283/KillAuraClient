package com.example.killaura.gui.widgets;

import com.example.killaura.core.settings.EnumSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Селектор для enum-настройки (выбор режима).
 * ЛКМ — следующий режим, ПКМ — предыдущий.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumSelectorWidget {
    private final EnumSetting setting;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public EnumSelectorWidget(EnumSetting<?> setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);
        MinecraftClient client = MinecraftClient.getInstance();

        // Подпись настройки
        context.drawTextWithShadow(
            client.textRenderer,
            Text.of(setting.getName()),
            x,
            y,
            0xFFCCCCCC
        );

        // Стрелки и значение
        String valueText = "‹ " + ((Enum<?>) setting.getValue()).name() + " ›";
        int valueColor = hovered ? 0xFF00d4ff : 0xFFFFFFFF;
        int textWidth = client.textRenderer.getWidth(valueText);
        int valueX = x + width - textWidth;
        context.drawTextWithShadow(client.textRenderer, Text.of(valueText), valueX, y, valueColor);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                setting.cycle();
                return true;
            } else if (button == 1) {
                setting.cycleBack();
                return true;
            }
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y - 2 && mouseY <= y + height + 2;
    }

    public EnumSetting<?> getSetting() {
        return setting;
    }
}
