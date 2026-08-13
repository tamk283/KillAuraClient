package com.example.killaura.gui;

import com.example.killaura.core.ModuleManager;
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
    private Category selectedCategory;

    public CategoryPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
        initButtons();
    }

    /**
     * Инициализирует кнопки категорий.
     */
    private void initButtons() {
        int y = 10;
        for (Category category : Category.values()) {
            buttons.add(new CategoryButton(category, 10, y, 100, 20));
            y += 25;
        }
    }

    /**
     * Рисует панель категорий.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param delta время с последнего кадра.
     */
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (CategoryButton button : buttons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    /**
     * Обрабатывает клик мыши.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param button кнопка мыши.
     * @return true, если клик обработан.
     */
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
     * Обрабатывает отпускание кнопки мыши.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param button кнопка мыши.
     * @return true, если отпускание кнопки обработано.
     */
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * Возвращает выбранную категорию.
     * @return выбранная категория.
     */
    public Category getSelectedCategory() {
        return selectedCategory;
    }

    /**
     * Категории модулей.
     */
    public enum Category {
        COMBAT, MOVEMENT, VISUALS, PLAYER, MISCELLANEOUS
    }
}