package com.example.killaura.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Кнопка категории.
 */
public class CategoryButton {
    private final CategoryPanel.Category category;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public CategoryButton(CategoryPanel.Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Рисует кнопку категории.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param delta время с последнего кадра.
     */
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean hovered = isHovered(mouseX, mouseY);
        int color = hovered ? 0xFF00d4ff : 0xFF1a1a2e;

        // Рисуем фон кнопки
        context.fill(x, y, x + width, y + height, color);

        // Рисуем текст кнопки (исправлено)
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(category.name()),
            x + 5,
            y + 5,
            0xFFFFFFFF
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
     * Возвращает категорию.
     * @return категория.
     */
    public CategoryPanel.Category getCategory() {
        return category;
    }
}