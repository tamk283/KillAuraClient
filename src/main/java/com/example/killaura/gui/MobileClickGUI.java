package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.util.List;

/**
 * Адаптированное меню для мобильной версии.
 */
public class MobileClickGUI {
    private static final MobileClickGUI INSTANCE = new MobileClickGUI();
    private final MinecraftClient client;
    private boolean open = false;
    private int selectedModuleIndex = 0;
    private final List<BaseModule> modules;

    private MobileClickGUI() {
        client = MinecraftClient.getInstance();
        modules = ModuleManager.getInstance().getAllModules();
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    /**
     * Возвращает экземпляр MobileClickGUI.
     * @return экземпляр MobileClickGUI.
     */
    public static MobileClickGUI getInstance() {
        return INSTANCE;
    }

    /**
     * Обрабатывает тики клиента.
     * @param client клиент Minecraft.
     */
    private void onTick(MinecraftClient client) {
        if (client.options.inventoryKey.wasPressed()) {
            toggle();
        }

        if (open) {
            if (client.options.jumpKey.wasPressed()) {
                selectedModuleIndex = (selectedModuleIndex - 1 + modules.size()) % modules.size();
            } else if (client.options.sneakKey.wasPressed()) {
                selectedModuleIndex = (selectedModuleIndex + 1) % modules.size();
            } else if (client.options.attackKey.wasPressed()) {
                modules.get(selectedModuleIndex).toggle();
            }
        }
    }

    /**
     * Переключает состояние меню.
     */
    public void toggle() {
        open = !open;
    }

    /**
     * Рисует меню на экране (HUD).
     */
    public void render(DrawContext drawContext, int width, int height) {
        if (!open || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Рисуем полупрозрачный фон
        drawContext.fill(0, 0, width, height, 0x801a1a2e);

        // Рисуем список модулей
        int y = height / 2 - (modules.size() * 10) / 2;
        for (int i = 0; i < modules.size(); i++) {
            BaseModule module = modules.get(i);
            int color = i == selectedModuleIndex ? 0xFF00d4ff : 0xFFFFFFFF;
            String status = module.isEnabled() ? "[ON]" : "[OFF]";
            String text = status + " " + module.getName();
            drawContext.drawTextWithShadow(textRenderer, Text.of(text), width / 2 - textRenderer.getWidth(text) / 2, y, color);
            y += 20;
        }

        // Рисуем инструкции
        String instructions = "Jump: ↑ | Sneak: ↓ | Attack: Toggle";
        drawContext.drawTextWithShadow(textRenderer, Text.of(instructions), width / 2 - textRenderer.getWidth(instructions) / 2, height - 30, 0xFFFFFFFF);
    }
}