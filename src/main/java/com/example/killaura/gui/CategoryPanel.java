package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
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
    private BaseModule.Category selectedCategory;

    public CategoryPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
        initButtons();
    }

    private void initButtons() {
        int y = 10;
        for (BaseModule.Category category : BaseModule.Category.values()) {
            buttons.add(new CategoryButton(category, 10, y, 100, 20));
            y += 25;
        }
        // По умолчанию выбираем первую категорию
        if (buttons.size() > 0) {
            selectedCategory = buttons.get(0).getCategory();
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (CategoryButton button : buttons) {
            button.render(context, mouseX, mouseY, delta, selectedCategory);
        }
    }

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

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public BaseModule.Category getSelectedCategory() {
        return selectedCategory;
    }
}
