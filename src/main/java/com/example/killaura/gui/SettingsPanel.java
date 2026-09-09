package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Панель настроек модуля.
 */
public class SettingsPanel {
    private BaseModule module;
    private int x;
    private int y;
    private int width;
    private int height;

    public SettingsPanel(ClickGUI parent) {
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Устанавливает модуль.
     * @param module модуль.
     */
    public void setModule(BaseModule module) {
        this.module = module;
    }

    /**
     * Рисует панель настроек.
     * @param context контекст отрисовки.
     */
    public void render(DrawContext context) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        context.fill(x, y, x + width, y + height, 0xDD10101E);
        context.drawTextWithShadow(textRenderer, Text.of("Module info"), x + 8, y + 8, 0xFF00D4FF);

        if (module == null) {
            context.drawTextWithShadow(textRenderer, Text.of("Tap a module to select it"), x + 8, y + 32, 0xFFAAAAAA);
            context.drawTextWithShadow(textRenderer, Text.of("Left click / tap toggles"), x + 8, y + 48, 0xFFAAAAAA);
            return;
        }

        int textX = x + 8;
        int textY = y + 32;
        context.drawTextWithShadow(textRenderer, Text.of(module.getName()), textX, textY, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, Text.of(module.getDescription()), textX, textY + 16, 0xFFCCCCCC);
        context.drawTextWithShadow(textRenderer, Text.of("Category: " + module.getCategory().getDisplayName()), textX, textY + 32, 0xFFCCCCCC);
        context.drawTextWithShadow(textRenderer, Text.of("State: " + (module.isEnabled() ? "ON" : "OFF")), textX, textY + 48, module.isEnabled() ? 0xFF00FF66 : 0xFFFF7777);
    }
}
