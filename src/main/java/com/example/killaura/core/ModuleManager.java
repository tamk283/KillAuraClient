package com.example.killaura.core;

import com.example.killaura.modules.CriticalsModule;
import com.example.killaura.modules.HitBoxesModule;
import com.example.killaura.modules.KillAuraModule;
import com.example.killaura.modules.ReachModule;
import com.example.killaura.modules.VelocityModule;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер модулей: регистрирует все модули и обновляет их каждый тик.
 */
public class ModuleManager {
    private static ModuleManager INSTANCE;
    private final List<BaseModule> modules = new ArrayList<>();
    private boolean initialized = false;

    private ModuleManager() {
    }

    public static ModuleManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleManager();
        }
        return INSTANCE;
    }

    /**
     * Регистрирует модули и обработчик тиков.
     * Должен вызываться на стороне клиента.
     */
    public void register() {
        if (initialized) return;
        initialized = true;

        // Combat модули
        modules.add(new KillAuraModule());
        modules.add(new CriticalsModule());
        modules.add(new ReachModule());
        modules.add(new VelocityModule());
        modules.add(new HitBoxesModule());

        // Обновление модулей каждый клиентский тик
        ClientTickEvents.END_CLIENT_TICK.register(client -> updateModules());
    }

    public List<BaseModule> getAllModules() {
        return modules;
    }

    /**
     * Возвращает модули указанной категории.
     */
    public List<BaseModule> getModulesByCategory(BaseModule.Category category) {
        List<BaseModule> result = new ArrayList<>();
        for (BaseModule module : modules) {
            if (module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    public BaseModule getModule(String name) {
        for (BaseModule module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void updateModules() {
        modules.forEach(BaseModule::update);
    }
}
