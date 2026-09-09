package com.example.killaura.gui;

import com.example.killaura.core.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Панель категорий.
 */
public class CategoryPanel {
    private final ClickGUI parent;
    private final List<CategoryButton> buttons;
    private ModuleCategory selectedCategory = ModuleCategory.COMBAT;
    private int x;
    private int y;
    private int width;
    private int height;
    private int buttonHeight = 22;
    private int gap = 6;

    public CategoryPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
        initButtons();
    }

    /**
     * Инициализирует кнопки категорий.
     */
    private void initButtons() {
        buttons.clear();
        for (ModuleCategory category : ModuleCategory.values()) {
            buttons.add(new CategoryButton(category));
        }
    }

    public void setBounds(int x, int y, int width, int height, int buttonHeight, int gap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonHeight = buttonHeight;
        this.gap = gap;
        updateButtonBounds();
    }

    private void updateButtonBounds() {
        int currentY = y + 22;
        for (CategoryButton button : buttons) {
            button.setBounds(x + 6, currentY, width - 12, buttonHeight);
            currentY += buttonHeight + gap;
        }
    }

    /**
     * Рисует панель категорий.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     */
    public void render(DrawContext context, int mouseX, int mouseY) {
        context.fill(x, y, x + width, y + height, 0xDD10101E);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of("Categories"), x + 8, y + 8, 0xFF00D4FF);

        for (CategoryButton button : buttons) {
            button.render(context, mouseX, mouseY, button.getCategory() == selectedCategory);
        }
    }

    /**
     * Обрабатывает клик мыши.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @return true, если клик обработан.
     */
    public boolean mouseClicked(double mouseX, double mouseY) {
        for (CategoryButton categoryButton : buttons) {
            if (categoryButton.isHovered(mouseX, mouseY)) {
                selectedCategory = categoryButton.getCategory();
                parent.getModuleListPanel().setCategory(selectedCategory);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает выбранную категорию.
     * @return выбранная категория.
     */
    public ModuleCategory getSelectedCategory() {
        return selectedCategory;
    }
}
