package com.example.killaura;

import com.example.killaura.core.ModuleManager;
import com.example.killaura.modules.KillAuraModule;
import net.fabricmc.api.ModInitializer;

public class KillAuraMod implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("KillAura Client initialized!");

        // Получаем менеджер модулей
        ModuleManager manager = ModuleManager.getInstance();

        // Находим KillAura и включаем её
        KillAuraModule killAura = (KillAuraModule) manager.getModule("KillAura");
        if (killAura != null) {
            killAura.enable();
            System.out.println("KillAura enabled!");
        } else {
            System.out.println("KillAura module not found!");
        }
    }
}