package com.chubbychump.hunterxhunter.server.abilities.heartstuff;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncHealthPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;

public class MoreHealth implements IMoreHealth {

    private byte version;
    private float modifier;
    private short rampPosition;
    private byte containers;

    public MoreHealth() {
        this.version = (byte) 1;
        this.rampPosition = 0;
    }

    @Override
    public byte getVersion() {
        return version;
    }

    @Override
    public float getModifier() {
        return modifier;
    }

    @Override
    public float getTrueModifier() {
        return modifier + (containers * 2);
    }

    @Override
    public float getEnhancerModifier() { return 1.5f; }

    @Override
    public short getRampPosition() {
        return rampPosition;
    }

    @Override
    public byte getHeartContainers() {
        return containers;
    }

    @Override
    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    @Override
    public void setRampPosition(short position) {
        this.rampPosition = position;
    }

    @Override
    public void addRampPosition() {
        this.rampPosition += (short) 1;
    }

    @Override
    public void setHeartContainers(byte amount) {
        this.containers = amount;
    }

    @Override
    public void addHeartContainer() {
        this.containers += (byte) 1;
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
        if (!player.getCommandSenderWorld().isClientSide) {
            AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            ClientboundUpdateAttributesPacket packet = new ClientboundUpdateAttributesPacket(player.getId(), Collections.singleton(attribute));
            ((ServerLevel) player.getCommandSenderWorld()).getChunkSource().broadcastAndSend(player, packet);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putByte("version", version);
        tag.putFloat("modifier", modifier);
        tag.putShort("position", rampPosition);
        tag.putByte("containers", containers);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setVersion(tag.getByte("version"));
        setModifier(tag.getFloat("modifier"));
        setRampPosition(tag.getShort("position"));
        setHeartContainers(tag.getByte("containers"));
    }

    public static IMoreHealth getFromPlayer(Player player) {
        return player.getCapability(MoreHealthProvider.CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }

    public static void updateClient(ServerPlayer player, IMoreHealth cap) {
        PacketManager.sendTo(player, new SyncHealthPacket(player.getId(), cap.serializeNBT()));
    }

    @Override
    public String toString() {
        return String.format("MoreHealth{version=%s,modifier=%s,rampPosition=%s, containers=%s}", version, modifier, rampPosition, containers);
    }

}
