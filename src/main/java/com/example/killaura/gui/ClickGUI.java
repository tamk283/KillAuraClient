package com.example.killaura.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Основное меню клиента (ClickGUI).
 * Открывается по Right Shift, содержит панели категорий, списка модулей и настроек.
 */
public class ClickGUI extends Screen {
    private static final ClickGUI INSTANCE = new ClickGUI();
    private final MinecraftClient client;
    private CategoryPanel categoryPanel;
    private ModuleListPanel moduleListPanel;
    private SettingsPanel settingsPanel;
    private boolean panelsInitialized = false;

    private ClickGUI() {
        super(Text.of("ClickGUI"));
        client = MinecraftClient.getInstance();
    }

    public static ClickGUI getInstance() {
        return INSTANCE;
    }

    private void initPanels() {
        if (panelsInitialized) return;
        panelsInitialized = true;
        categoryPanel = new CategoryPanel(this);
        moduleListPanel = new ModuleListPanel(this);
        settingsPanel = new SettingsPanel(this);
        if (categoryPanel.getSelectedCategory() != null) {
            moduleListPanel.setCategory(categoryPanel.getSelectedCategory());
        }
    }

    @Override
    protected void init() {
        initPanels();
    }

    public void open() {
        if (client != null && client.player != null) {
            initPanels();
            client.setScreen(this);
        }
    }

    public void close() {
        if (client != null) {
            client.setScreen(null);
        }
    }

    public boolean isOpen() {
        return client != null && client.currentScreen == this;
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
    public boolean mouseDragged(double mouseX, double mouseY, int button, double startX, double startY) {
        if (settingsPanel != null) settingsPanel.mouseDragged(mouseX, mouseY, button);
        return super.mouseDragged(mouseX, mouseY, button, startX, startY);
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
