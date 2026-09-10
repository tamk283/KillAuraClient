package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

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
        if (client.player == null || modules.isEmpty() || client.currentScreen != null) {
            if (client.currentScreen != null) {
                open = false;
            }
            selectedModuleIndex = 0;
            return;
        }

        if (selectedModuleIndex >= modules.size()) {
            selectedModuleIndex = modules.size() - 1;
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

    public void close() {
        open = false;
    }

    /**
     * Рисует меню на экране (HUD).
     */
    public void render(DrawContext drawContext, int width, int height) {
        if (!open || client.player == null || modules.isEmpty() || client.currentScreen != null) return;

        TextRenderer textRenderer = client.textRenderer;

        int safeWidth = Math.max(1, width);
        int safeHeight = Math.max(1, height);
        int panelWidth = Math.max(1, Math.min(safeWidth - 8, 280));
        int rowHeight = safeHeight < 420 ? 24 : 28;
        int maxPanelHeight = Math.max(1, safeHeight - 8);
        int panelHeight = Math.max(1, Math.min(maxPanelHeight, 34 + modules.size() * rowHeight + 24));
        int x = Math.max(0, (safeWidth - panelWidth) / 2);
        int y = Math.max(0, (safeHeight - panelHeight) / 2);

        drawContext.fill(x, y, x + panelWidth, y + panelHeight, 0xDD10101E);
        drawContext.drawTextWithShadow(textRenderer, Text.of("Mobile modules"), x + 10, y + 10, 0xFF00D4FF);

        int listTop = y + 32;
        int listBottom = y + panelHeight - 22;
        if (listBottom > listTop) {
            drawContext.enableScissor(x, listTop, x + panelWidth, listBottom);
            int currentY = listTop;
            for (int i = 0; i < modules.size(); i++) {
                if (currentY > listBottom) {
                    break;
                }

                BaseModule module = modules.get(i);
                boolean selected = i == selectedModuleIndex;
                int rowColor = selected ? 0xFF243A55 : 0xFF151527;
                int accentColor = module.isEnabled() ? 0xFF00FF66 : 0xFFFF5555;
                int rowRight = Math.max(x + 9, x + panelWidth - 8);
                drawContext.fill(x + 8, currentY, rowRight, currentY + rowHeight - 4, rowColor);
                drawContext.fill(x + 8, currentY, x + 11, currentY + rowHeight - 4, accentColor);

                String status = module.isEnabled() ? "ON" : "OFF";
                String text = module.getName() + "  [" + status + "]";
                int textColor = selected ? 0xFF00D4FF : 0xFFFFFFFF;
                drawContext.drawTextWithShadow(textRenderer, Text.of(text), x + 17, currentY + (rowHeight - 12) / 2, textColor);
                currentY += rowHeight;
            }
            drawContext.disableScissor();
        }

        String instructions = "Jump/Sneak: Select | Attack: Toggle | Right Ctrl: Close";
        int instructionX = x + Math.max(8, (panelWidth - textRenderer.getWidth(instructions)) / 2);
        drawContext.drawTextWithShadow(textRenderer, Text.of(instructions), instructionX, y + panelHeight - 18, 0xFFCCCCCC);
    }
}
