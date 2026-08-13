package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Панель настроек модуля.
 */
public class SettingsPanel {
    private final ClickGUI parent;
    private BaseModule module;

    public SettingsPanel(ClickGUI parent) {
        this.parent = parent;
    }

    /**
     * Устанавливает модуль.
     * @param module модуль.
     */
    public void setModule(BaseModule module) {
        this.module = module;
    }

    /**
     * Рисует панель настроек.
     * @param context контекст отрисовки.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param delta время с последнего кадра.
     */
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (module == null) return;

        // Рисуем фон панели настроек
        context.fill(10, 300, 610, 390, 0xFF1a1a2e);

        // Рисуем заголовок (исправлено)
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(module.getName()),
            20, 310, 0xFFFFFFFF
        );

        // TODO: Добавить отрисовку настроек модуля
    }

    /**
     * Обрабатывает клик мыши.
     * @param mouseX позиция курсора по оси X.
     * @param mouseY позиция курсора по оси Y.
     * @param button кнопка мыши.
     * @return true, если клик обработан.
     */
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
}