package com.example.killaura.core;

import com.example.killaura.modules.KillAuraModule;
import com.example.killaura.modules.SpeedModule;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<BaseModule> modules;

    private ModuleManager() {
        modules = new ArrayList<>();
        registerModules();
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
        List<BaseModule> filteredModules = new ArrayList<>();
        for (BaseModule module : modules) {
            if (module.getCategory() == category) {
                filteredModules.add(module);
            }
        }
        return filteredModules;
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
