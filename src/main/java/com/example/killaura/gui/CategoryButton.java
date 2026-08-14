package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Кнопка категории.
 */
public class CategoryButton {
    private final BaseModule.Category category;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public CategoryButton(BaseModule.Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta, BaseModule.Category selected) {
        boolean hovered = isHovered(mouseX, mouseY);
        boolean selectedCategory = category == selected;
        int color;
        if (selectedCategory) {
            color = 0xFF16213e;
        } else if (hovered) {
            color = 0xFF0f3460;
        } else {
            color = 0xFF1a1a2e;
        }

        context.fill(x, y, x + width, y + height, color);
        // Полоса-индикатор выбранной категории
        if (selectedCategory) {
            context.fill(x, y, x + 2, y + height, 0xFF00d4ff);
        }

        int textColor = selectedCategory ? 0xFF00d4ff : 0xFFFFFFFF;
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(category.name()),
            x + 8,
            y + 6,
            textColor
        );
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public BaseModule.Category getCategory() {
        return category;
    }
}
