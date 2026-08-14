package com.example.killaura.gui;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Рендерит HUD: список включённых модулей (arraylist) в правом верхнем углу.
 */
public class HudRenderer {
    private static boolean registered = false;

    public static void register() {
        if (registered) return;
        registered = true;

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;
            // Не рисуем HUD поверх открытого ClickGUI
            if (client.currentScreen != null) return;

            List<BaseModule> enabled = new ArrayList<>();
            for (BaseModule module : ModuleManager.getInstance().getAllModules()) {
                if (module.isEnabled()) {
                    enabled.add(module);
                }
            }

            if (enabled.isEmpty()) return;

            int screenWidth = client.getWindow().getScaledWidth();
            int y = 2;
            for (BaseModule module : enabled) {
                String text = module.getName();
                int textWidth = client.textRenderer.getWidth(text);
                drawContext.drawTextWithShadow(
                    client.textRenderer,
                    Text.of(text),
                    screenWidth - textWidth - 2,
                    y,
                    0xFF00d4ff
                );
                y += client.textRenderer.fontHeight + 1;
            }
        });
    }
}
