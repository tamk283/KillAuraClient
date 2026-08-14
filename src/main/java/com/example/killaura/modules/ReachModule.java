package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;

/**
 * Reach — увеличивает эффективную дальность атаки.
 * Реализован как параметр, который KillAura может учитывать при поиске цели.
 */
public class ReachModule extends BaseModule {
    private final MinecraftClient client;
    private final NumberSetting reach;

    public ReachModule() {
        super("Reach", "Увеличивает дальность атаки", Category.COMBAT);
        this.client = MinecraftClient.getInstance();
        this.reach = addSetting(new NumberSetting("Reach", "Дополнительная дальность", 0.5, 0.0, 2.0, 0.1));
    }

    /**
     * Возвращает дополнительную дальность, добавляемую к базовому радиусу.
     */
    public double getReachBonus() {
        return reach.getDouble();
    }
}
