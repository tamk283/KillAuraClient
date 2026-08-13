package com.example.killaura.core;

import com.example.killaura.modules.KillAuraModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<BaseModule> modules;

    private ModuleManager() {
        modules = new ArrayList<>();
        registerModules();
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private void registerModules() {
        modules.add(new KillAuraModule());
    }

    public List<BaseModule> getAllModules() {
        return modules;
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