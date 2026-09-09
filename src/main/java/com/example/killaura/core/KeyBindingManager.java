package com.example.killaura.core;

import com.example.killaura.gui.ClickGUI;
import com.example.killaura.gui.MobileClickGUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Менеджер горячих клавиш, отвечает за управление горячими клавишами.
 */
public class KeyBindingManager {
    private static final KeyBindingManager INSTANCE = new KeyBindingManager();
    private KeyBinding openGuiKey;
    private KeyBinding openMobileGuiKey;

    private KeyBindingManager() {
        registerKeyBindings();
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    /**
     * Возвращает экземпляр KeyBindingManager.
     * @return экземпляр KeyBindingManager.
     */
    public static KeyBindingManager getInstance() {
        return INSTANCE;
    }

    /**
     * Регистрирует горячие клавиши.
     */
    private void registerKeyBindings() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.killaura.opengui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.killaura"
        ));

        openMobileGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.killaura.openmobilegui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_CONTROL,
            "category.killaura"
        ));
    }

    /**
     * Обрабатывает тики клиента.
     * @param client клиент Minecraft.
     */
    private void onTick(MinecraftClient client) {
        while (openGuiKey.wasPressed()) {
            ClickGUI clickGUI = ClickGUI.getInstance();
            if (client.currentScreen == clickGUI) {
                clickGUI.close();
            } else if (client.currentScreen == null) {
                clickGUI.open();
            }
        }

        while (openMobileGuiKey.wasPressed()) {
            if (client.currentScreen == null && client.player != null) {
                MobileClickGUI.getInstance().toggle();
            }
        }
    }
}
