package com.chubbychump.hunterxhunter.common.abilities.heartstuff;

import com.chubbychump.hunterxhunter.Config;

import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncHealthPacket;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.attributes.Attributes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SEntityPropertiesPacket;
import net.minecraft.world.server.ServerWorld;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider.CAPABILITY;


public class MoreHealth implements IMoreHealth {

    private byte version;
    private float modifier;
    private short rampPosition;
    private byte containers;

    public MoreHealth() {
        this.version = (byte) 1;
        this.modifier = getDefaultModifier();
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
    public float getEnhancerModifier() {
        return modifier + (containers * 3);
    }

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
    public void synchronise(PlayerEntity player) {
        if (!player.getEntityWorld().isRemote) {
            ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            SEntityPropertiesPacket packet = new SEntityPropertiesPacket(player.getEntityId(), Collections.singleton(attribute));
            ((ServerWorld) player.getEntityWorld()).getChunkProvider().sendToTrackingAndSelf(player, packet);
        }
    }


    public static float getDefaultModifier() {
        return Config.defHealth.get() - (float) Attributes.MAX_HEALTH.getDefaultValue();
    }



    public static IMoreHealth getFromPlayer(PlayerEntity player) {
        return player.getCapability(CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }

    public static void updateClient(ServerPlayerEntity player, IMoreHealth cap) {
        PacketManager.sendTo(player, new SyncHealthPacket(player.getEntityId(), (CompoundNBT) CAPABILITY.writeNBT(cap, null)));
    }

    @Override
    public String toString() {
        return String.format("MoreHealth{version=%s,modifier=%s,rampPosition=%s, containers=%s}", version, modifier, rampPosition, containers);
    }

}

