package com.example.killaura.core;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Менеджер конфигурации, отвечает за сохранение и загрузку настроек.
 */
public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Properties properties;
    private final Path configFile;

    private ConfigManager() {
        properties = new Properties();
        configFile = FabricLoader.getInstance().getConfigDir().resolve("killaura/client.properties");
        loadConfig();
    }

    /**
     * Возвращает экземпляр ConfigManager.
     * @return экземпляр ConfigManager.
     */
    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    /**
     * Сохраняет строковую настройку.
     * @param key ключ настройки.
     * @param value значение настройки.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveConfig();
    }

    /**
     * Возвращает строковую настройку.
     * @param key ключ настройки.
     * @param defaultValue значение по умолчанию.
     * @return значение настройки или значение по умолчанию, если настройка не найдена.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Сохраняет boolean-настройку.
     * @param key ключ настройки.
     * @param value значение настройки.
     */
    public void setBoolean(String key, boolean value) {
        setProperty(key, Boolean.toString(value));
    }

    /**
     * Возвращает boolean-настройку.
     * @param key ключ настройки.
     * @param defaultValue значение по умолчанию.
     * @return значение настройки или значение по умолчанию при отсутствии/ошибке формата.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        if (value.equalsIgnoreCase("true")) {
            return true;
        }
        if (value.equalsIgnoreCase("false")) {
            return false;
        }

        return defaultValue;
    }

    /**
     * Сохраняет конфигурацию в файл.
     */
    public void saveConfig() {
        try {
            Path parent = configFile.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (OutputStream output = Files.newOutputStream(configFile)) {
                properties.store(output, "KillAura Client Settings");
            }
        } catch (IOException e) {
            System.err.println("Failed to save KillAura config: " + e.getMessage());
        }
    }

    /**
     * Загружает конфигурацию из файла.
     */
    private void loadConfig() {
        if (!Files.exists(configFile)) {
            saveConfig();
            return;
        }

        try (InputStream input = Files.newInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load KillAura config, using defaults: " + e.getMessage());
            saveConfig();
        }
    }
}
