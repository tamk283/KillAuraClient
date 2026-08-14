package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.ModuleManager;
import com.example.killaura.core.settings.BooleanSetting;
import com.example.killaura.core.settings.EnumSetting;
import com.example.killaura.core.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * KillAura — автоматическая атака ближайшего врага.
 * Настройки: range, attackDelay, rotationSpeed, throughWalls, onlyPlayers, targetingMode.
 * Интегрируется с Reach (бонус дальности), Criticals (крит перед атакой).
 */
public class KillAuraModule extends BaseModule {
    private final MinecraftClient client;
    private LivingEntity target;
    private long lastAttackTime = 0;

    private final NumberSetting range;
    private final NumberSetting attackDelay;
    private final NumberSetting rotationSpeed;
    private final BooleanSetting throughWalls;
    private final BooleanSetting onlyPlayers;
    private final EnumSetting<TargetingMode> targetingMode;

    public enum TargetingMode {
        CLOSEST, HEALTH, ANGLE
    }

    public KillAuraModule() {
        super("KillAura", "Автоматическая атака ближайшего врага", Category.COMBAT);

        this.client = MinecraftClient.getInstance();
        this.range = addSetting(new NumberSetting("Range", "Дальность атаки", 4.5, 3.0, 6.0, 0.1));
        this.attackDelay = addSetting(new NumberSetting("Delay", "Задержка между атаками (мс)", 200, 50, 1000, 10));
        this.rotationSpeed = addSetting(new NumberSetting("RotSpeed", "Скорость поворота", 0.5, 0.05, 1.0, 0.05));
        this.throughWalls = addSetting(new BooleanSetting("ThroughWalls", "Атака сквозь стены", false));
        this.onlyPlayers = addSetting(new BooleanSetting("OnlyPlayers", "Только игроки", true));
        this.targetingMode = addSetting(new EnumSetting<>("Target", "Приоритет цели", TargetingMode.CLOSEST));
    }

    @Override
    protected void onEnable() {
        if (client.player != null) {
            client.player.sendMessage(Text.of("§aKillAura enabled!"), false);
        }
    }

    @Override
    protected void onDisable() {
        target = null;
        if (client.player != null) {
            client.player.sendMessage(Text.of("§cKillAura disabled!"), false);
        }
    }

    @Override
    protected void onUpdate() {
        if (client.player == null || client.world == null) return;
        if (client.interactionManager == null) return;

        ClientPlayerEntity player = client.player;

        target = findTarget(player);
        if (target == null) return;

        rotateTo(player, target);

        long now = System.currentTimeMillis();
        if (now - lastAttackTime < attackDelay.getLong()) return;

        // Крит перед атакой, если модуль включён
        CriticalsModule criticals = (CriticalsModule) ModuleManager.getInstance().getModule("Criticals");
        if (criticals != null && criticals.isEnabled()) {
            criticals.performCritical();
        }

        client.interactionManager.attackEntity(player, target);
        player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = now;
    }

    private LivingEntity findTarget(ClientPlayerEntity player) {
        // Учитываем бонус Reach, если модуль включён
        double effectiveRange = range.getDouble();
        ReachModule reachModule = (ReachModule) ModuleManager.getInstance().getModule("Reach");
        if (reachModule != null && reachModule.isEnabled()) {
            effectiveRange += reachModule.getReachBonus();
        }

        List<LivingEntity> entities = new ArrayList<>();

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living == player) continue;
            if (!living.isAlive()) continue;
            if (living.getHealth() <= 0) continue;

            if (onlyPlayers.isEnabled() && !(living instanceof PlayerEntity)) continue;

            if (!throughWalls.isEnabled() && !player.canSee(living)) continue;

            double dist = player.distanceTo(living);
            if (dist > effectiveRange) continue;

            entities.add(living);
        }

        if (entities.isEmpty()) return null;

        switch (targetingMode.getValue()) {
            case HEALTH:
                entities.sort(Comparator.comparingDouble(LivingEntity::getHealth));
                break;
            case ANGLE:
                entities.sort(Comparator.comparingDouble(e -> angleTo(player, e)));
                break;
            case CLOSEST:
            default:
                entities.sort(Comparator.comparingDouble(e -> player.distanceTo(e)));
                break;
        }
        return entities.get(0);
    }

    private double angleTo(ClientPlayerEntity player, LivingEntity entity) {
        double dx = entity.getX() - player.getX();
        double dz = entity.getZ() - player.getZ();
        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float diff = MathHelper.wrapDegrees(targetYaw - player.getYaw());
        return Math.abs(diff);
    }

    private void rotateTo(ClientPlayerEntity player, LivingEntity target) {
        double dx = target.getX() - player.getX();
        double dz = target.getZ() - player.getZ();
        double dy = target.getEyeY() - player.getEyeY();

        double dist = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, dist));

        float currentYaw = player.getYaw();
        float currentPitch = player.getPitch();

        // Нормализуем разницу yaw с учётом оборота ±180
        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = targetPitch - currentPitch;

        float speed = rotationSpeed.getFloat();
        float newYaw = currentYaw + yawDiff * speed;
        float newPitch = currentPitch + pitchDiff * speed;

        player.setYaw(newYaw);
        player.setPitch(newPitch);
    }
}
