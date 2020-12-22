package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;

public class NenUser {
    protected int nenPower = 1;
    protected int currentNen;
    public boolean nenActivated;
    public boolean gyo;
    public boolean en;
    public boolean ren;
    public boolean zetsu;
    protected int nencolor = 0;

    public NenUser() {
        currentNen = 0;
    }

    public static void updateClient(ServerPlayerEntity player, NenUser cap) {
        PacketManager.sendTo(player, new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }

    public static void updateServer(PlayerEntity player, NenUser cap) {
        PacketManager.sendToServer(new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }

    public void setNenPower(int power) {
        this.nenPower = power;
    }

    public void increaseNenPower(PlayerEntity theEntity) {
        if (nenPower < 30) {
            nenPower += 1;
            HunterXHunter.LOGGER.info("User nen power has increased. Total is now " + nenPower);
        }
        else {
            HunterXHunter.LOGGER.info("Nen Power is capped at 30. Current power is " + nenPower);
        }

    }

    public void resetNen() {
        gyo = false;
        en = false;
        ren = false;
        zetsu = false;
        nenActivated = false;
    }

    public void toggleNen() {
        this.nenActivated = !this.nenActivated;
        HunterXHunter.LOGGER.info("Nen toggled, nen is now " + nenActivated);
        if (nenActivated == false) {
            resetNen();
        }
    }

    public void copy(NenUser other) {
        this.setCurrentNen(other.getCurrentNen());
        this.setNenPower(other.getNenPower());
        //TODO: carry over boolean states?
    }

    public static NenUser getFromPlayer(PlayerEntity player) {
        return player.getCapability(NENUSER, null).orElseThrow(() -> new IllegalArgumentException("NenUser must not be empty!"));
    }

    public int getMaxCurrentNen() {
        return nenPower * 100;
    }

    public boolean isNenActivated() {
        return nenActivated;
    }
    public int getNenPower() {
        return nenPower;
    }
    public int getCurrentNen() {
        return currentNen;
    }
    public void setCurrentNen(int newNen) {
        this.currentNen = newNen;
    }
    public void setNencolor(int color) {
        this.nencolor = color;
    }
    public int getNenType() {
        return 0;
    }
    public int getNencolor() { return nencolor; }
    public void toggleGyo() {
        gyo = !gyo;
    }
    public boolean getGyo() {
        return gyo;
    }
    public void toggleEn() {
        en = !en;
    }
    public boolean getEn() {
        return en;
    }
    public void toggleZetsu() {
        zetsu = !zetsu;
    }
    public boolean getZetsu() {
        return zetsu;
    }
    public void toggleRen() { ren = !ren; }
    public boolean getRen() {
        return ren;
    }
    public void nenpower1(PlayerEntity player) { return; }
}