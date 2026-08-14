package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Панель списка модулей выбранной категории.
 */
public class ModuleListPanel {
    private final ClickGUI parent;
    private final List<ModuleButton> buttons;
    private BaseModule.Category category;
    private int scrollOffset = 0;
    private static final int VISIBLE_HEIGHT = 300;
    private static final int BUTTON_HEIGHT = 24;
    private static final int BUTTON_SPACING = 4;
    private static final int LIST_X = 120;
    private static final int LIST_Y = 10;
    private static final int LIST_WIDTH = 200;

    public ModuleListPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
    }

    public void setCategory(BaseModule.Category category) {
        this.category = category;
        updateButtons();
    }

    private void updateButtons() {
        buttons.clear();
        if (category == null) return;
        int y = LIST_Y - scrollOffset;
        List<BaseModule> modules = ModuleManager.getInstance().getModulesByCategory(category);
        for (BaseModule module : modules) {
            buttons.add(new ModuleButton(module, LIST_X, y, LIST_WIDTH, BUTTON_HEIGHT));
            y += BUTTON_HEIGHT + BUTTON_SPACING;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Фон панели списка
        context.fill(LIST_X - 4, LIST_Y - 4, LIST_X + LIST_WIDTH + 4, LIST_Y + VISIBLE_HEIGHT, 0x80000000);
        for (ModuleButton button : buttons) {
            // Пропускаем кнопки за пределами видимой области
            if (button.getY() + BUTTON_HEIGHT < LIST_Y || button.getY() > LIST_Y + VISIBLE_HEIGHT) continue;
            button.render(context, mouseX, mouseY, delta);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ModuleButton moduleButton : buttons) {
            if (moduleButton.isHovered(mouseX, mouseY)) {
                if (button == 0) {
                    moduleButton.getModule().toggle();
                } else if (button == 1) {
                    parent.getSettingsPanel().setModule(moduleButton.getModule());
                }
                return true;
            }
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        int maxScroll = Math.max(0, buttons.size() * (BUTTON_HEIGHT + BUTTON_SPACING) - VISIBLE_HEIGHT);
        scrollOffset -= (int) amount * 20;
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset));
        updateButtons();
        return true;
    }
}
