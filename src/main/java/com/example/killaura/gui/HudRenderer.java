package com.example.killaura.gui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class HudRenderer {
    private static boolean registered = false;

    public static void register() {
        if (registered) return;
        registered = true;

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            // Рендеринг меню
            MobileClickGUI.getInstance().render(drawContext, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
        });
    }
}