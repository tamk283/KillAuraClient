package com.example.killaura.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Менеджер конфигурации, отвечает за сохранение и загрузку настроек.
 */
public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Properties properties;
    private final String configFile = "config/client.properties";

    private ConfigManager() {
        properties = new Properties();
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
     * Сохраняет настройку.
     * @param key ключ настройки.
     * @param value значение настройки.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveConfig();
    }

    /**
     * Возвращает значение настройки.
     * @param key ключ настройки.
     * @param defaultValue значение по умолчанию.
     * @return значение настройки или значение по умолчанию, если настройка не найдена.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Сохраняет конфигурацию в файл.
     */
    private void saveConfig() {
        try (FileOutputStream output = new FileOutputStream(configFile)) {
            properties.store(output, "Client Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает конфигурацию из файла.
     */
    private void loadConfig() {
        try (FileInputStream input = new FileInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            // Файл не существует или ошибка чтения, используем значения по умолчанию
            saveConfig();
        }
    }
}