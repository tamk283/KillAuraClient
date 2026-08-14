package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * Velocity — уменьшает откидывание при получении урона.
 * Корректирует скорость игрока каждый тик, компенсируя knockback.
 */
public class VelocityModule extends BaseModule {
    private final MinecraftClient client;
    private final NumberSetting horizontal;
    private final NumberSetting vertical;

    public VelocityModule() {
        super("Velocity", "Уменьшает откидывание", Category.COMBAT);
        this.client = MinecraftClient.getInstance();
        this.horizontal = addSetting(new NumberSetting("Horizontal", "Горизонтальная компенсация (%)", 0, 0, 100, 5));
        this.vertical = addSetting(new NumberSetting("Vertical", "Вертикальная компенсация (%)", 0, 0, 100, 5));
    }

    @Override
    protected void onUpdate() {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        // Компенсируем только аномальное горизонтальное ускорение (knockback),
        // не затрагивая обычное движение игрока.
        double hFactor = horizontal.getDouble() / 100.0;
        if (hFactor > 0 && player.horizontalCollision) {
            player.setVelocity(
                player.getVelocity().x * (1 - hFactor),
                player.getVelocity().y,
                player.getVelocity().z * (1 - hFactor)
            );
        }

        double vFactor = vertical.getDouble() / 100.0;
        if (vFactor > 0 && player.getVelocity().y > 0.4) {
            player.setVelocity(
                player.getVelocity().x,
                player.getVelocity().y * (1 - vFactor),
                player.getVelocity().z
            );
        }
    }
}
