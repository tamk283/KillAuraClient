package com.example.killaura.gui;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

/**
 * Основное меню клиента.
 */
public class ClickGUI extends Screen {
    private static final ClickGUI INSTANCE = new ClickGUI();
    private boolean open = false;
    private CategoryPanel categoryPanel;
    private ModuleListPanel moduleListPanel;
    private SettingsPanel settingsPanel;
    private KeyBinding openGuiKey;
    private final MinecraftClient client;

    private ClickGUI() {
        super(Text.of("ClickGUI"));
        client = MinecraftClient.getInstance();
        categoryPanel = new CategoryPanel(this);
        moduleListPanel = new ModuleListPanel(this);
        settingsPanel = new SettingsPanel(this);
        registerKeyBinding();
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public static ClickGUI getInstance() {
        return INSTANCE;
    }

    private void registerKeyBinding() {
        openGuiKey = new KeyBinding(
            "key.killaura.opengui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.killaura"
        );
    }

    private void onTick(MinecraftClient client) {
        if (openGuiKey.wasPressed() && client.currentScreen == null && client.player != null && !client.player.isDead() && client.interactionManager != null && client.interactionManager.getCurrentGameMode() != GameMode.CREATIVE) {
            if (isOpen()) {
                close();
            } else {
                open();
            }
        }
    }

    public void open() {
        if (client != null && client.currentScreen == null && client.player != null && !client.player.isDead() && client.interactionManager != null && client.interactionManager.getCurrentGameMode() != GameMode.CREATIVE) {
            open = true;
            client.setScreen(this);
        }
    }

    public void close() {
        open = false;
        if (client != null) {
            client.setScreen(null);
        }
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (client == null) return;
        context.fill(0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), 0x801a1a2e);

        if (categoryPanel != null) categoryPanel.render(context, mouseX, mouseY, delta);
        if (moduleListPanel != null) moduleListPanel.render(context, mouseX, mouseY, delta);
        if (settingsPanel != null) settingsPanel.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (categoryPanel != null) categoryPanel.mouseClicked(mouseX, mouseY, button);
        if (moduleListPanel != null) moduleListPanel.mouseClicked(mouseX, mouseY, button);
        if (settingsPanel != null) settingsPanel.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (categoryPanel != null) categoryPanel.mouseReleased(mouseX, mouseY, button);
        if (moduleListPanel != null) moduleListPanel.mouseReleased(mouseX, mouseY, button);
        if (settingsPanel != null) settingsPanel.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (moduleListPanel != null) moduleListPanel.mouseScrolled(mouseX, mouseY, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public CategoryPanel getCategoryPanel() { return categoryPanel; }
    public ModuleListPanel getModuleListPanel() { return moduleListPanel; }
    public SettingsPanel getSettingsPanel() { return settingsPanel; }
}