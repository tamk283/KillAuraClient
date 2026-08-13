package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Кнопка модуля.
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

    /**
     * Рисует кнопку модуля.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param delta время с последнего кадра.
     */
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);
        int color = hovered ? 0xFF00d4ff : 0xFF1a1a2e;
        int textColor = module.isEnabled() ? 0xFF00FF00 : 0xFFFFFFFF;

        // Рисуем фон кнопки
        context.fill(x, y, x + width, y + height, color);

        // Рисуем текст кнопки (исправлено)
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(module.getName()),
            x + 5,
            y + 5,
            textColor
        );
    }

    /**
     * Проверяет, находится ли курсор над кнопкой.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @return true, если курсор находится над кнопкой.
     */
    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    /**
     * Возвращает модуль.
     * @return модуль.
     */
    public BaseModule getModule() {
        return module;
    }
}