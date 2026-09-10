package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

/**
 * Модуль для увеличения скорости движения игрока.
 */
public class SpeedModule extends BaseModule {
    private final MinecraftClient client;
    private final int effectDuration = 20; // Длительность эффекта в тиках. Короткая, чтобы быстро затухала после выключения.
    private final int effectAmplifier = 1; // Уровень Speed (0 = Speed I, 1 = Speed II)
    private long lastEffectTime = 0;
    private final long effectDelay = 250; // Задержка между применениями эффекта (мс)

    public SpeedModule() {
        super("Speed", "Увеличение скорости движения", ModuleCategory.MOVEMENT);
        this.client = MinecraftClient.getInstance();
        System.out.println("SpeedModule created!");
    }

    @Override
    protected void onEnable() {
        System.out.println("Speed ENABLED");
        if (client.player != null) {
            client.player.sendMessage(Text.of("§aSpeed enabled!"), false);
        }
    }

    @Override
    protected void onDisable() {
        System.out.println("Speed DISABLED");
        lastEffectTime = 0;
        if (client.player != null) {
            client.player.sendMessage(Text.of("§cSpeed disabled!"), false);
            // Не удаляем StatusEffects.SPEED принудительно: у игрока мог быть легальный эффект скорости.
            // Наш короткий эффект сам исчезнет примерно за секунду.
        }
    }

    @Override
    protected void onUpdate() {
        if (client.player == null || client.world == null) return;

        ClientPlayerEntity player = client.player;

        // Проверка движения игрока
        boolean isMoving = player.input.movementSideways != 0 || player.input.movementForward != 0;

        if (!isMoving) {
            return;
        }

        // Применяем эффект скорости с задержкой.
        // Не умножаем velocity вручную каждый тик, чтобы скорость не росла экспоненциально.
        long now = System.currentTimeMillis();
        if (now - lastEffectTime >= effectDelay) {
            player.addStatusEffect(
                new StatusEffectInstance(
                    StatusEffects.SPEED,
                    effectDuration,
                    effectAmplifier,
                    false, // показывать ли частицы
                    false  // показывать ли иконку эффекта
                )
            );
            lastEffectTime = now;
        }
    }
}
