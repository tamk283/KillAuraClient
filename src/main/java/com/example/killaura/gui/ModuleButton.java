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
    private int x;
    private int y;
    private int width;
    private int height;

    public ModuleButton(BaseModule module) {
        this.module = module;
    }

    public void setBounds(int x, int y, int width, int height) {
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
     */
    public void render(DrawContext context, int mouseX, int mouseY) {
        boolean hovered = isHovered(mouseX, mouseY);
        int color = module.isEnabled() ? 0xFF16351F : hovered ? 0xFF243A55 : 0xFF151527;
        int accentColor = module.isEnabled() ? 0xFF00FF66 : 0xFFFF5555;
        int textColor = module.isEnabled() ? 0xFFB6FFC8 : 0xFFFFFFFF;

        context.fill(x, y, x + width, y + height, color);
        context.fill(x, y, x + 3, y + height, accentColor);

        String status = module.isEnabled() ? "ON" : "OFF";
        String text = module.getName() + "  [" + status + "]";
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(text),
            x + 9,
            y + (height - 8) / 2,
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

    public boolean intersects(int top, int bottom) {
        return y + height >= top && y <= bottom;
    }

    /**
     * Возвращает модуль.
     * @return модуль.
     */
    public BaseModule getModule() {
        return module;
    }
}
