package com.example.killaura.modules;

import com.example.killaura.core.BaseModule;
import com.example.killaura.core.settings.EnumSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * Criticals — делает удары критическими.
 * Режим Jump: мини-прыжок перед атакой. Режим Packet: имитация падения.
 */
public class CriticalsModule extends BaseModule {
    private final MinecraftClient client;
    private final EnumSetting<Mode> mode;

    public enum Mode {
        JUMP, PACKET
    }

    public CriticalsModule() {
        super("Criticals", "Критические удары", Category.COMBAT);
        this.client = MinecraftClient.getInstance();
        this.mode = addSetting(new EnumSetting<>("Mode", "Режим", Mode.JUMP));
    }

    /**
     * Вызывается KillAura перед атакой, чтобы обеспечить крит.
     */
    public void performCritical() {
        if (client.player == null || client.player.isOnGround()) {
            if (mode.getValue() == Mode.JUMP) {
                client.player.jump();
            }
            // PACKET режим оставлен как заглушка — требует сетевой пакет
            // (ванилльный клиент не может отправить fall-state без mixin)
        }
    }
}
