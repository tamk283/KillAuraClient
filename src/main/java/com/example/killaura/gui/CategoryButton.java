package com.example.killaura.gui;

import com.example.killaura.core.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Кнопка категории.
 */
public class CategoryButton {
    private final ModuleCategory category;
    private int x;
    private int y;
    private int width;
    private int height;

    public CategoryButton(ModuleCategory category) {
        this.category = category;
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
    }

    /**
     * Рисует кнопку категории.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param selected true, если категория выбрана.
     */
    public void render(DrawContext context, int mouseX, int mouseY, boolean selected) {
        boolean hovered = isHovered(mouseX, mouseY);
        int color = selected ? 0xFF0077AA : hovered ? 0xFF243A55 : 0xFF151527;
        int borderColor = selected ? 0xFF00D4FF : 0xFF2C2C44;

        context.fill(x, y, x + width, y + height, color);
        context.fill(x, y, x + 2, y + height, borderColor);

        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(category.getDisplayName()),
            x + 8,
            y + (height - 8) / 2,
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
    public ModuleCategory getCategory() {
        return category;
    }
}
