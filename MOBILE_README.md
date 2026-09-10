# KillAura Client - Mobile Optimized Edition

Оптимизированный клиент для мобильных Java лаунчеров (Zalith Launcher, PojavLauncher и т.д.).

## 📱 Совместимость

- ✅ Zalith Launcher
- ✅ PojavLauncher
- ✅ Minecraft PE (с поддержкой Fabric)
- ✅ Minecraft Java Desktop Edition

## 🔧 Требования

- **Java 17+** (оптимально для мобильных)
- **Minecraft 1.21.4** (Fabric)
- **Android 10+** (для мобильных устройств)

## 📦 Установка

### На мобильном (Zalith/Pojav)

1. Скачайте оптимизированный JAR:
   ```
   KillAuraClient-mobile-1.0.0.jar
   ```

2. Поместите в папку модов:
   - **Zalith**: `storage/mods/` или `games/mods/`
   - **PojavLauncher**: `.pojav/mods/`

3. Перезагрузите лаунчер

### На ПК

```bash
./gradlew build
# Скопируйте build/libs/KillAuraClient-1.0.0.jar в .minecraft/mods/
```

## 🎮 Управление (Мобильная версия)

### Сенсорное управление GUI
- **Двойной тап** - открыть/закрыть меню (вместо Right Shift)
- **Свайп вверх/вниз** - прокрутка списка модулей
- **Тап по модулю** - включить/отключить

### Клавиатурные сокращения
- **Right Shift** или **F5** - открыть меню (если подключена клавиатура)

## ⚙️ Модули

1. **KillAura** - Автоматическая атака ближайшего врага
   - Диапазон: 4.5 блока
   - Плавное наведение: 0.5x скорость
   - Только игроки по умолчанию

2. **Speed** - Увеличение скорости движения
   - Множитель скорости: 1.5x
   - Применяется при движении

## 🛠️ Сборка

### Стандартная версия (ПК)
```bash
./gradlew build
```

### Оптимизированная версия (Мобильная)
```bash
./gradlew buildMobile
```

Результат: `build/libs/KillAuraClient-mobile-*.jar` (уменьшенный размер, оптимизирован для мобильных)

## 🚀 Оптимизация для мобильных

- ✅ Java 17 (вместо Java 21) для лучшей совместимости
- ✅ Удаление лишних файлов (Maven, Kotlin артефактов)
- ✅ Автоматическое определение платформы
- ✅ MobileClickGUI - адаптированный интерфейс для сенсора
- ✅ Оптимизированная обработка ввода

## 📝 Добавление своих модулей

Создайте класс в `src/main/java/com/example/killaura/modules/`:

```java
package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import net.minecraft.client.MinecraftClient;

public class MyModule extends BaseModule {
    private final MinecraftClient client;

    public MyModule() {
        super("MyModule", "Описание модуля");
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void onEnable() {
        System.out.println("MyModule enabled!");
    }

    @Override
    protected void onDisable() {
        System.out.println("MyModule disabled!");
    }

    @Override
    protected void onUpdate() {
        // Логика обновления каждый тик
    }
}
```

Затем зарегистрируйте в `ModuleManager.registerModules()`:
```java
modules.add(new MyModule());
```

## 🐛 Отладка

Проверьте логи:
- **ПК**: `%appdata%/.minecraft/logs/latest.log` (Windows)
- **Zalith**: Встроенный логовизор
- **PojavLauncher**: Встроенный логовизор

Ищите строки вида:
```
[KillAura] KillAura Client initialized!
[KillAura] Running on: MOBILE/DESKTOP
```

## 📄 Лицензия

MIT License

## 👨‍💻 Автор

tamk283

---

**Версия**: 1.0.0-mobile  
**Последнее обновление**: 2026-09-10
