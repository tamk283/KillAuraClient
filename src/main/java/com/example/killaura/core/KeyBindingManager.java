package com.example.killaura.core;

import com.example.killaura.gui.ClickGUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Менеджер горячих клавиш.
 * Right Shift — открыть/закрыть ClickGUI.
 */
public class KeyBindingManager {
    private static KeyBindingManager INSTANCE;
    private final MinecraftClient client;
    private KeyBinding openGuiKey;
    private boolean initialized = false;

    private KeyBindingManager() {
        client = MinecraftClient.getInstance();
    }

    public static KeyBindingManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeyBindingManager();
        }
        return INSTANCE;
    }

    /**
     * Регистрирует горячие клавиши и обработчик тиков.
     * Должен вызываться на стороне клиента (ClientModInitializer).
     */
    public void register() {
        if (initialized) return;
        initialized = true;

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.killaura.opengui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.killaura"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(MinecraftClient client) {
        if (openGuiKey != null && openGuiKey.wasPressed()) {
            if (client.currentScreen == null) {
                ClickGUI.getInstance().open();
            } else if (ClickGUI.getInstance().isOpen()) {
                ClickGUI.getInstance().close();
            }
        }
    }
}
