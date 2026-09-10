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
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
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
        if (width <= 0 || height <= 0) {
            return;
        }

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        context.fill(x, y, x + width, y + height, 0xDD10101E);
        context.drawTextWithShadow(textRenderer, Text.of("Module info"), x + 8, y + 8, 0xFF00D4FF);

        context.enableScissor(x, y, x + width, y + height);
        if (module == null) {
            drawIfVisible(context, textRenderer, "Tap a module to select it", x + 8, y + 32, 0xFFAAAAAA);
            drawIfVisible(context, textRenderer, "Left click / tap toggles", x + 8, y + 48, 0xFFAAAAAA);
            context.disableScissor();
            return;
        }

        int textX = x + 8;
        int textY = y + 32;
        drawIfVisible(context, textRenderer, module.getName(), textX, textY, 0xFFFFFFFF);
        drawIfVisible(context, textRenderer, module.getDescription(), textX, textY + 16, 0xFFCCCCCC);
        drawIfVisible(context, textRenderer, "Category: " + module.getCategory().getDisplayName(), textX, textY + 32, 0xFFCCCCCC);
        drawIfVisible(context, textRenderer, "State: " + (module.isEnabled() ? "ON" : "OFF"), textX, textY + 48, module.isEnabled() ? 0xFF00FF66 : 0xFFFF7777);
        context.disableScissor();
    }

    private void drawIfVisible(DrawContext context, TextRenderer textRenderer, String text, int textX, int textY, int color) {
        if (textY + 8 <= y || textY >= y + height) {
            return;
        }
        context.drawTextWithShadow(textRenderer, Text.of(text), textX, textY, color);
    }
}
