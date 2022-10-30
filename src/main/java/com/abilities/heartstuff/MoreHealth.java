package com.abilities.heartstuff;

import com.packets.PacketManager;
import com.packets.SyncHealthPacket;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;

import static com.abilities.heartstuff.MoreHealthProvider.MORE_HEALTH_CAPABILITY;

@Getter
@Setter
public class MoreHealth implements IMoreHealth {
    private byte version;
    private float modifier;
    private short rampPosition;
    private byte heartContainers;

    public MoreHealth() {
        this.version = (byte) 1;
        this.modifier = getDefaultModifier();
        this.rampPosition = 0;
    }

    @Override
    public float getTrueModifier() {
        return modifier + (heartContainers * 2);
    }

    @Override
    public float getEnhancerModifier() {
        return modifier + (heartContainers * 3);
    }

    @Override
    public void addRampPosition() {
        this.rampPosition += (short) 1;
    }

    @Override
    public void addHeartContainer() {
        this.heartContainers += (byte) 1;
    }

    @Override
    public void copy(IMoreHealth other) {
        this.setVersion(other.getVersion());
        this.setModifier(other.getModifier());
        this.setRampPosition(other.getRampPosition());
        this.setHeartContainers(other.getHeartContainers());
    }

    @Override
    public void synchronise(Player player) {
        if (!player.getLevel().isClientSide) {
            AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            ClientboundUpdateAttributesPacket packet = new ClientboundUpdateAttributesPacket(player.getId(), Collections.singleton(attribute));
            ((ServerLevel) player.getLevel()).getChunkSource().broadcastAndSend(player, packet);
        }
    }

    public static float getDefaultModifier() {
        return 1.0F;
    }

    public static IMoreHealth getFromPlayer(Player player) {
        return player.getCapability(MORE_HEALTH_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }

    public static void updateClient(ServerPlayer player, IMoreHealth cap) {
        PacketManager.sendTo(player, new SyncHealthPacket(player.getId(), cap.serializeNBT()));
    }

    @Override
    public String toString() {
        return String.format("MoreHealth {version=%s,modifier=%s,rampPosition=%s, containers=%s}", version, modifier, rampPosition, heartContainers);
    }
}