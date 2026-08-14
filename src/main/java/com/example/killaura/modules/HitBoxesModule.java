package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

/**
 * HitBoxes — расширяет хитбоксы живых существ для более лёгкого попадания.
 * Используется KillAura при проверке попадания.
 */
public class HitBoxesModule extends BaseModule {
    private final MinecraftClient client;
    private final NumberSetting expansion;

    public HitBoxesModule() {
        super("HitBoxes", "Расширяет хитбоксы врагов", Category.COMBAT);
        this.client = MinecraftClient.getInstance();
        this.expansion = addSetting(new NumberSetting("Expansion", "Расширение хитбокса", 0.2, 0.0, 1.0, 0.05));
    }

    /**
     * Возвращает расширенный хитбокс сущности или null, если расширение не применимо.
     */
    public Box getExpandedBox(Entity entity) {
        if (!(entity instanceof LivingEntity)) return null;
        double exp = expansion.getDouble();
        if (exp <= 0) return entity.getBoundingBox();
        Box box = entity.getBoundingBox();
        return box.expand(exp, exp / 2, exp);
    }
}
