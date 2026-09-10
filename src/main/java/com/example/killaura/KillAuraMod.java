package com.example.killaura;

import com.example.killaura.core.ModuleManager;
import com.example.killaura.modules.KillAuraModule;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class KillAuraMod implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("KillAura Client initialized!");
        
        // Проверяем окружение (мобильное или ПК)
        boolean isMobile = detectMobileEnvironment();
        System.out.println("Running on: " + (isMobile ? "MOBILE" : "DESKTOP"));

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

    /**
     * Определяет, запущен ли мод на мобильном устройстве
     */
    private boolean detectMobileEnvironment() {
        try {
            // Проверяем свойства системы
            String osName = System.getProperty("os.name", "").toLowerCase();
            String javaVendor = System.getProperty("java.vendor", "").toLowerCase();
            
            // Признаки мобильной платформы
            boolean isAndroid = osName.contains("android") || 
                               System.getProperty("java.vm.name", "").contains("Android");
            
            // Проверяем доступность сенсорного экрана
            boolean hasTouchScreen = System.getProperty("java.touch.screen", "false").equals("true");
            
            return isAndroid || hasTouchScreen;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
