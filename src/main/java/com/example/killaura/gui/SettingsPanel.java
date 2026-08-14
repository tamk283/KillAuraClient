package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.settings.BooleanSetting;
import com.example.killaura.core.settings.EnumSetting;
import com.example.killaura.core.settings.NumberSetting;
import com.example.killaura.core.settings.Setting;
import com.example.killaura.gui.widgets.EnumSelectorWidget;
import com.example.killaura.gui.widgets.SliderWidget;
import com.example.killaura.gui.widgets.ToggleWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Панель настроек выбранного модуля.
 * Динамически строит виджеты (слайдеры, переключатели, селекторы) из настроек модуля.
 */
public class SettingsPanel {
    private final ClickGUI parent;
    private BaseModule module;
    private final List<Object> widgets = new ArrayList<>();

    // Геометрия панели (правая часть ClickGUI)
    private static final int PANEL_X = 340;
    private static final int PANEL_Y = 10;
    private static final int PANEL_WIDTH = 280;
    private static final int PANEL_HEIGHT = 300;
    private static final int WIDGET_START_Y = 40;
    private static final int WIDGET_SPACING = 28;
    private static final int SLIDER_HEIGHT = 10;

    public SettingsPanel(ClickGUI parent) {
        this.parent = parent;
    }

    public void setModule(BaseModule module) {
        this.module = module;
        buildWidgets();
    }

    public BaseModule getModule() {
        return module;
    }

    private void buildWidgets() {
        widgets.clear();
        if (module == null) return;
        int y = WIDGET_START_Y;
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof NumberSetting numberSetting) {
                widgets.add(new SliderWidget(numberSetting, PANEL_X + 10, y, PANEL_WIDTH - 20, SLIDER_HEIGHT));
                y += WIDGET_SPACING;
            } else if (setting instanceof BooleanSetting booleanSetting) {
                widgets.add(new ToggleWidget(booleanSetting, PANEL_X + 10, y, PANEL_WIDTH - 20, 14));
                y += WIDGET_SPACING - 6;
            } else if (setting instanceof EnumSetting<?> enumSetting) {
                widgets.add(new EnumSelectorWidget(enumSetting, PANEL_X + 10, y, PANEL_WIDTH - 20, 12));
                y += WIDGET_SPACING - 6;
            }
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (module == null) return;

        // Фон панели
        context.fill(PANEL_X, PANEL_Y, PANEL_X + PANEL_WIDTH, PANEL_Y + PANEL_HEIGHT, 0xFF1a1a2e);
        // Заголовок
        context.fill(PANEL_X, PANEL_Y, PANEL_X + PANEL_WIDTH, PANEL_Y + 24, 0xFF0f3460);
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(module.getName() + " - Settings"),
            PANEL_X + 8,
            PANEL_Y + 8,
            0xFFFFFFFF
        );
        // Состояние
        String status = module.isEnabled() ? "[ON]" : "[OFF]";
        int statusColor = module.isEnabled() ? 0xFF00ff88 : 0xFFff5555;
        context.drawTextWithShadow(
            MinecraftClient.getInstance().textRenderer,
            Text.of(status),
            PANEL_X + PANEL_WIDTH - 40,
            PANEL_Y + 8,
            statusColor
        );

        // Виджеты
        for (Object widget : widgets) {
            if (widget instanceof SliderWidget slider) {
                slider.render(context, mouseX, mouseY, delta);
            } else if (widget instanceof ToggleWidget toggle) {
                toggle.render(context, mouseX, mouseY, delta);
            } else if (widget instanceof EnumSelectorWidget selector) {
                selector.render(context, mouseX, mouseY, delta);
            }
        }

        // Подсказка по пустой панели
        if (widgets.isEmpty()) {
            context.drawTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.of("No settings available"),
                PANEL_X + 10,
                WIDGET_START_Y,
                0xFF888888
            );
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (module == null) return false;
        for (Object widget : widgets) {
            if (widget instanceof SliderWidget slider && slider.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (widget instanceof ToggleWidget toggle && toggle.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (widget instanceof EnumSelectorWidget selector && selector.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (module == null) return false;
        boolean handled = false;
        for (Object widget : widgets) {
            if (widget instanceof SliderWidget slider && slider.mouseReleased(mouseX, mouseY, button)) {
                handled = true;
            }
        }
        return handled;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (module == null) return false;
        boolean handled = false;
        for (Object widget : widgets) {
            if (widget instanceof SliderWidget slider && slider.mouseDragged(mouseX, mouseY, button)) {
                handled = true;
            }
        }
        return handled;
    }
}
