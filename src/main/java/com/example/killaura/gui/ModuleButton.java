package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Кнопка модуля в списке.
 */
public class ModuleButton {
    private final BaseModule module;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ModuleButton(BaseModule module, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);
        boolean enabled = module.isEnabled();
        int bg;
        if (enabled) {
            bg = 0xFF0f3460;
        } else if (hovered) {
            bg = 0xFF16213e;
        } else {
            bg = 0xFF1a1a2e;
        }

        context.fill(x, y, x + width, y + height, bg);
        // Полоса состояния слева
        context.fill(x, y, x + 2, y + height, enabled ? 0xFF00ff88 : 0xFF3a3a4a);

        int textColor = enabled ? 0xFF00ff88 : 0xFFFFFFFF;
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(module.getName()),
            x + 8,
            y + height / 2 - 4,
            textColor
        );

        // Индикатор настроек (если есть настройки)
        if (!module.getSettings().isEmpty()) {
            String indicator = "»";
            int indicatorColor = hovered ? 0xFF00d4ff : 0xFF888888;
            context.drawTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.of(indicator),
                x + width - 12,
                y + height / 2 - 4,
                indicatorColor
            );
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public BaseModule getModule() {
        return module;
    }

    public int getY() {
        return y;
    }
}
