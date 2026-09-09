package com.example.killaura;

import com.example.killaura.core.ConfigManager;
import com.example.killaura.core.KeyBindingManager;
import com.example.killaura.core.ModuleManager;
import com.example.killaura.gui.ClickGUI;
import com.example.killaura.gui.HudRenderer;
import com.example.killaura.gui.MobileClickGUI;
import net.fabricmc.api.ClientModInitializer;

public class KillAuraMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("KillAura Client initialized!");

        ConfigManager.getInstance();
        ModuleManager.getInstance();
        KeyBindingManager.getInstance();
        ClickGUI.getInstance();
        MobileClickGUI.getInstance();
        HudRenderer.register();
    }
}
