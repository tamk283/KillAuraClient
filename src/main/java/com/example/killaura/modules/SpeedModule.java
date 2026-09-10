package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

/**
 * Модуль для увеличения скорости движения игрока.
 */
public class SpeedModule extends BaseModule {
    private final MinecraftClient client;

    // Горизонтальная скорость задаётся напрямую, без ванильного эффекта Speed.
    private final double groundSpeed = 0.34;
    private final double airSpeed = 0.28;
    private final double sneakMultiplier = 0.35;

    public SpeedModule() {
        super("Speed", "Ускорение движения персонажа", ModuleCategory.MOVEMENT);
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
        if (client.player != null) {
            client.player.sendMessage(Text.of("§cSpeed disabled!"), false);
        }
    }

    @Override
    protected void onUpdate() {
        if (client.player == null || client.world == null) return;

        ClientPlayerEntity player = client.player;
        float forward = player.input.movementForward;
        float sideways = player.input.movementSideways;

        if (forward == 0.0F && sideways == 0.0F) {
            return;
        }

        applyHorizontalSpeed(player, forward, sideways);
    }

    private void applyHorizontalSpeed(ClientPlayerEntity player, float forward, float sideways) {
        double inputLength = Math.sqrt(forward * forward + sideways * sideways);
        if (inputLength <= 0.0D) {
            return;
        }

        double normalizedForward = forward / inputLength;
        double normalizedSideways = sideways / inputLength;
        double yawRadians = Math.toRadians(player.getYaw());
        double sin = Math.sin(yawRadians);
        double cos = Math.cos(yawRadians);

        double directionX = normalizedSideways * cos - normalizedForward * sin;
        double directionZ = normalizedForward * cos + normalizedSideways * sin;
        double speed = player.isOnGround() ? groundSpeed : airSpeed;
        if (player.isSneaking()) {
            speed *= sneakMultiplier;
        }

        Vec3d velocity = player.getVelocity();
        player.setVelocity(directionX * speed, velocity.y, directionZ * speed);
    }
}
