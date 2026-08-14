# KillAura Client

Combat-клиент для Minecraft Fabric 1.21.4.

## Требования

- Minecraft 1.21.4
- Fabric Loader ≥ 0.15.11
- Fabric API
- Java 21

## Сборка

```bash
./gradlew build
```

Готовый мод окажется в `build/libs/KillAuraClient-1.0.0.jar`.

## Использование

- **Right Shift** — открыть/закрыть ClickGUI
- В ClickGUI: **ЛКМ** по модулю — вкл/выкл, **ПКМ** — открыть настройки
- HUD показывает список активных модулей в правом верхнем углу

## Модули (Combat)

| Модуль       | Описание                                       | Настройки                                                  |
|--------------|------------------------------------------------|------------------------------------------------------------|
| KillAura     | Автоматическая атака ближайшего врага           | Range, Delay, RotSpeed, ThroughWalls, OnlyPlayers, Target  |
| Criticals    | Критические удары                              | Mode (Jump/Packet)                                         |
| Reach        | Увеличивает дальность атаки                     | Reach (бонус 0–2)                                          |
| Velocity     | Уменьшает откидывание                           | Horizontal %, Vertical %                                   |
| HitBoxes     | Расширяет хитбоксы врагов                       | Expansion                                                  |

## Структура

```
src/main/java/com/example/killaura/
├── KillAuraMod.java          — точка входа (ClientModInitializer)
├── core/
│   ├── BaseModule.java       — базовый класс модуля + Category
│   ├── ModuleManager.java    — регистрация модулей + тики
│   ├── KeyBindingManager.java — Right Shift → ClickGUI
│   ├── ConfigManager.java    — сохранение настроек
│   └── settings/             — Setting, NumberSetting, BooleanSetting, EnumSetting
├── gui/
│   ├── ClickGUI.java         — основной экран меню
│   ├── CategoryPanel/Button  — панель категорий
│   ├── ModuleListPanel/Button — список модулей
│   ├── SettingsPanel.java    — панель настроек (динамические виджеты)
│   ├── HudRenderer.java      — HUD (arraylist)
│   └── widgets/              — SliderWidget, ToggleWidget, EnumSelectorWidget
└── modules/
    ├── KillAuraModule.java
    ├── CriticalsModule.java
    ├── ReachModule.java
    ├── VelocityModule.java
    └── HitBoxesModule.java
```

## Лицензия

MIT
