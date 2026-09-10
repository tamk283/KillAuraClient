package com.example.killaura.core;

import com.example.killaura.modules.KillAuraModule;
import com.example.killaura.modules.SpeedModule;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<BaseModule> modules;
    private final Map<ModuleCategory, List<BaseModule>> modulesByCategory;

    private ModuleManager() {
        modules = new ArrayList<>();
        modulesByCategory = new EnumMap<>(ModuleCategory.class);
        registerModules();
        rebuildCategoryCache();
        loadModuleStates();
        ClientTickEvents.END_CLIENT_TICK.register(client -> updateModules());
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private void registerModules() {
        register(new KillAuraModule());
        register(new SpeedModule());
    }

    private void register(BaseModule module) {
        modules.add(module);
    }

    private void rebuildCategoryCache() {
        for (ModuleCategory category : ModuleCategory.values()) {
            modulesByCategory.put(category, new ArrayList<>());
        }

        for (BaseModule module : modules) {
            modulesByCategory.get(module.getCategory()).add(module);
        }

        for (ModuleCategory category : ModuleCategory.values()) {
            modulesByCategory.put(category, Collections.unmodifiableList(modulesByCategory.get(category)));
        }
    }

    private void loadModuleStates() {
        ConfigManager configManager = ConfigManager.getInstance();
        for (BaseModule module : modules) {
            module.restoreEnabledState(configManager.getBoolean(module.getConfigKey(), false));
        }
    }

    public List<BaseModule> getAllModules() {
        return Collections.unmodifiableList(modules);
    }

    public List<BaseModule> getModulesByCategory(ModuleCategory category) {
        return modulesByCategory.getOrDefault(category, Collections.emptyList());
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
        for (BaseModule module : modules) {
            try {
                module.update();
            } catch (RuntimeException e) {
                System.err.println("Module update failed for " + module.getName() + ": " + e.getMessage());
                module.disable();
            }
        }
    }
}
