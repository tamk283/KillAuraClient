package com.example.killaura.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Основное меню клиента.
 */
public class ClickGUI extends Screen {
    private static final ClickGUI INSTANCE = new ClickGUI();
    private boolean open = false;
    private final CategoryPanel categoryPanel;
    private final ModuleListPanel moduleListPanel;
    private final SettingsPanel settingsPanel;
    private final MinecraftClient client;
    private int lastWidth = -1;
    private int lastHeight = -1;

    private ClickGUI() {
        super(Text.of("ClickGUI"));
        client = MinecraftClient.getInstance();
        categoryPanel = new CategoryPanel(this);
        moduleListPanel = new ModuleListPanel(this);
        settingsPanel = new SettingsPanel(this);
        moduleListPanel.setCategory(categoryPanel.getSelectedCategory());
    }

    public static ClickGUI getInstance() {
        return INSTANCE;
    }

    public void open() {
        if (client != null && client.currentScreen == null && client.player != null && !client.player.isDead()) {
            MobileClickGUI.getInstance().close();
            open = true;
            client.setScreen(this);
        }
    }

    public void close() {
        open = false;
        if (client != null && client.currentScreen == this) {
            client.setScreen(null);
        }
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void removed() {
        open = false;
        super.removed();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        updateLayout(width, height);
    }

    private void updateLayoutIfNeeded() {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        if (width != lastWidth || height != lastHeight) {
            updateLayout(width, height);
        }
    }

    private void updateLayout(int width, int height) {
        lastWidth = width;
        lastHeight = height;

        boolean compact = width < 620 || height < 420;
        int margin = compact ? 6 : 12;
        int gap = compact ? 6 : 10;
        int buttonHeight = compact ? 28 : 24;
        int contentHeight = Math.max(120, height - margin * 2);

        int categoryWidth = clamp(width / 4, compact ? 92 : 112, compact ? 130 : 160);
        int moduleWidth = clamp(width / 3, compact ? 150 : 190, compact ? 240 : 280);
        int settingsX = margin + categoryWidth + gap + moduleWidth + gap;
        int settingsWidth = width - settingsX - margin;

        if (settingsWidth >= 170) {
            categoryPanel.setBounds(margin, margin, categoryWidth, contentHeight, buttonHeight, gap);
            moduleListPanel.setBounds(margin + categoryWidth + gap, margin, moduleWidth, contentHeight, buttonHeight, gap);
            settingsPanel.setBounds(settingsX, margin, settingsWidth, contentHeight);
            return;
        }

        int settingsHeight = clamp(height / 3, 88, 130);
        int topHeight = Math.max(100, height - margin * 3 - settingsHeight);
        int availableTopWidth = width - margin * 2 - gap;
        categoryWidth = clamp(availableTopWidth / 3, 90, 140);
        moduleWidth = Math.max(130, availableTopWidth - categoryWidth);

        categoryPanel.setBounds(margin, margin, categoryWidth, topHeight, buttonHeight, gap);
        moduleListPanel.setBounds(margin + categoryWidth + gap, margin, moduleWidth, topHeight, buttonHeight, gap);
        settingsPanel.setBounds(margin, margin + topHeight + gap, width - margin * 2, settingsHeight);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (client == null) return;
        updateLayoutIfNeeded();

        context.fill(0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), 0x9910101E);

        categoryPanel.render(context, mouseX, mouseY);
        moduleListPanel.render(context, mouseX, mouseY);
        settingsPanel.render(context);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (categoryPanel.mouseClicked(mouseX, mouseY)) return true;
        if (moduleListPanel.mouseClicked(mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (moduleListPanel.mouseScrolled(mouseX, mouseY, verticalAmount)) return true;
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public CategoryPanel getCategoryPanel() { return categoryPanel; }
    public ModuleListPanel getModuleListPanel() { return moduleListPanel; }
    public SettingsPanel getSettingsPanel() { return settingsPanel; }
}
