package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Панель списка модулей.
 */
public class ModuleListPanel {
    private final ClickGUI parent;
    private final List<ModuleButton> buttons;
    private CategoryPanel.Category category;
    private int scrollOffset;

    public ModuleListPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
    }

    /**
     * Устанавливает категорию.
     * @param category категория.
     */
    public void setCategory(CategoryPanel.Category category) {
        this.category = category;
        updateButtons();
    }

    /**
     * Обновляет список кнопок модулей.
     */
    private void updateButtons() {
        buttons.clear();
        int y = 10;
        List<BaseModule> modules = ModuleManager.getInstance().getAllModules();
        for (BaseModule module : modules) {
            buttons.add(new ModuleButton(module, 120, y, 200, 20));
            y += 25;
        }
    }

    /**
     * Рисует панель списка модулей.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param delta время с последнего кадра.
     */
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (ModuleButton button : buttons) {
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
     * Обрабатывает прокрутку мыши.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param amount количество прокрутки.
     * @return true, если прокрутка обработана.
     */
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scrollOffset += (int) amount * 10;
        scrollOffset = Math.max(0, scrollOffset);
        return true;
    }
}