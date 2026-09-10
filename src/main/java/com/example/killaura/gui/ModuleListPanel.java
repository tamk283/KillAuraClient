package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleCategory;
import com.example.killaura.core.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Панель списка модулей.
 */
public class ModuleListPanel {
    private final ClickGUI parent;
    private final List<ModuleButton> buttons;
    private ModuleCategory category = ModuleCategory.COMBAT;
    private int x;
    private int y;
    private int width;
    private int height;
    private int buttonHeight = 22;
    private int gap = 6;
    private int scrollOffset;

    public ModuleListPanel(ClickGUI parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
        updateButtons();
    }

    /**
     * Устанавливает категорию.
     * @param category категория.
     */
    public void setCategory(ModuleCategory category) {
        this.category = category;
        this.scrollOffset = 0;
        updateButtons();
        updateButtonBounds();
    }

    public void setBounds(int x, int y, int width, int height, int buttonHeight, int gap) {
        this.x = x;
        this.y = y;
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        this.buttonHeight = Math.max(16, buttonHeight);
        this.gap = Math.max(2, gap);
        scrollOffset = Math.min(scrollOffset, getMaxScrollOffset());
        updateButtonBounds();
    }

    /**
     * Обновляет список кнопок модулей.
     */
    private void updateButtons() {
        buttons.clear();
        List<BaseModule> modules = ModuleManager.getInstance().getModulesByCategory(category);
        for (BaseModule module : modules) {
            buttons.add(new ModuleButton(module));
        }
    }

    private void updateButtonBounds() {
        int currentY = y + 22 - scrollOffset;
        int buttonWidth = Math.max(1, width - 12);
        for (ModuleButton button : buttons) {
            button.setBounds(x + 6, currentY, buttonWidth, buttonHeight);
            currentY += buttonHeight + gap;
        }
    }

    private int getContentHeight() {
        if (buttons.isEmpty()) {
            return 0;
        }
        return buttons.size() * buttonHeight + (buttons.size() - 1) * gap;
    }

    private int getMaxScrollOffset() {
        int visibleHeight = Math.max(0, height - 28);
        return Math.max(0, getContentHeight() - visibleHeight);
    }

    /**
     * Рисует панель списка модулей.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     */
    public void render(DrawContext context, int mouseX, int mouseY) {
        context.fill(x, y, x + width, y + height, 0xDD10101E);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of(category.getDisplayName()), x + 8, y + 8, 0xFF00D4FF);

        if (buttons.isEmpty()) {
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of("No modules"), x + 8, y + 32, 0xFFAAAAAA);
            return;
        }

        if (y + height > y + 22) {
            context.enableScissor(x, y + 22, x + width, y + height);
            for (ModuleButton button : buttons) {
                if (button.intersects(y + 22, y + height)) {
                    button.render(context, mouseX, mouseY);
                }
            }
            context.disableScissor();
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
        if (mouseX < x || mouseX > x + width || mouseY < y + 22 || mouseY > y + height) {
            return false;
        }

        for (ModuleButton moduleButton : buttons) {
            if (moduleButton.isHovered(mouseX, mouseY)) {
                parent.getSettingsPanel().setModule(moduleButton.getModule());
                if (button == 0) {
                    moduleButton.getModule().toggle();
                }
                return true;
            }
        }
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
        if (mouseX < x || mouseX > x + width || mouseY < y || mouseY > y + height) {
            return false;
        }

        scrollOffset -= (int) Math.round(amount * (buttonHeight + gap));
        scrollOffset = Math.max(0, Math.min(scrollOffset, getMaxScrollOffset()));
        updateButtonBounds();
        return true;
    }
}
