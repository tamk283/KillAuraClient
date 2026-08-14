package com.example.killaura;

import com.example.killaura.core.KeyBindingManager;
import com.example.killaura.core.ModuleManager;
import com.example.killaura.gui.HudRenderer;
import net.fabricmc.api.ClientModInitializer;

/**
 * Точка входа клиентской части мода.
 * Регистрирует модули, горячие клавиши и HUD.
 */
public class KillAuraMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("KillAura Client initializing...");

        ModuleManager.getInstance().register();
        KeyBindingManager.getInstance().register();
        HudRenderer.register();

        System.out.println("KillAura Client initialized!");
        System.out.println("Press Right Shift to open ClickGUI");
    }
}
