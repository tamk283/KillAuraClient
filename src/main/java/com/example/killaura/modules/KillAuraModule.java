package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class KillAuraModule extends BaseModule {
    private final MinecraftClient client;
    private LivingEntity target;
    private long lastAttackTime = 0;

    private final double range = 4.5;
    private final double rotationSpeed = 0.5;
    private final boolean throughWalls = false;
    private final boolean onlyPlayers = true;
    private final long attackDelay = 200;

    public KillAuraModule() {
        super("KillAura", "Автоматическая атака ближайшего врага", ModuleCategory.COMBAT);
        this.client = MinecraftClient.getInstance();
        System.out.println("KillAuraModule created!");
    }

    @Override
    protected void onEnable() {
        System.out.println("KillAura ENABLED");
        if (client.player != null) {
            client.player.sendMessage(Text.of("§aKillAura enabled!"), false);
        }
    }

    @Override
    protected void onDisable() {
        System.out.println("KillAura DISABLED");
        target = null;
        if (client.player != null) {
            client.player.sendMessage(Text.of("§cKillAura disabled!"), false);
        }
    }

    @Override
    protected void onUpdate() {
        if (client.player == null || client.world == null || client.interactionManager == null) return;

        ClientPlayerEntity player = client.player;

        // Поиск цели без временных списков/сортировок, чтобы не создавать мусор каждый тик.
        target = findTarget(player);

        if (target == null) return;

        // Поворот к цели
        rotateTo(player, target);

        // Атака с задержкой и учетом ванильного cooldown оружия
        long now = System.currentTimeMillis();
        if (now - lastAttackTime < attackDelay) return;
        if (player.getAttackCooldownProgress(0.0F) < 1.0F) return;

        // Атака
        client.interactionManager.attackEntity(player, target);
        player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = now;
    }

    private LivingEntity findTarget(ClientPlayerEntity player) {
        LivingEntity closest = null;
        double closestDistanceSquared = range * range;

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living == player) continue;
            if (!living.isAlive()) continue;
            if (living.getHealth() <= 0) continue;

            if (onlyPlayers && !(living instanceof PlayerEntity)) continue;

            double distanceSquared = player.squaredDistanceTo(living);
            if (distanceSquared > closestDistanceSquared) continue;

            // Проверка через стены выполняется после дешевой проверки дистанции.
            if (!throughWalls && !player.canSee(living)) continue;

            closest = living;
            closestDistanceSquared = distanceSquared;
        }

        return closest;
    }

    private void rotateTo(ClientPlayerEntity player, LivingEntity target) {
        double dx = target.getX() - player.getX();
        double dz = target.getZ() - player.getZ();
        double dy = target.getEyeY() - player.getEyeY();

        double dist = MathHelper.sqrt((float) (dx * dx + dz * dz));
        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, dist));

        // Плавный поворот с корректной обработкой перехода через -180/180 градусов
        float yawDelta = MathHelper.wrapDegrees(targetYaw - player.getYaw());
        float pitchDelta = targetPitch - player.getPitch();

        float newYaw = player.getYaw() + (float) (yawDelta * rotationSpeed);
        float newPitch = MathHelper.clamp(
            player.getPitch() + (float) (pitchDelta * rotationSpeed),
            -90.0F,
            90.0F
        );

        player.setYaw(newYaw);
        player.setPitch(newPitch);
    }
}
