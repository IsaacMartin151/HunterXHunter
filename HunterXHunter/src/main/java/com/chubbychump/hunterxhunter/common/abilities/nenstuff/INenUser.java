package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;

public interface INenUser {
    void setType(int nenType);
    boolean openedBook();
    void setOpenedBook(boolean opened);
    int getBurnout();
    boolean getOverlay();
    void setBurnout(int burn);
    static void updateClient(ServerPlayerEntity player, NenUser cap) {
        PacketManager.sendTo(player, new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }
    static void updateServer(PlayerEntity player, NenUser cap) {
        PacketManager.sendToServer(new SyncNenPacket(player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(cap, null)));
    }
    void setClipping(boolean clip);
    boolean getClipping();
    int getPassivePower();
    void setPassivePower(int i);
    void setNenPower(int power);
    void keybind1();
    void increaseNenPower();
    void resetNen();
    void toggleNen();
    int getFailCounter();
    void setFailCounter(int counter);
    void copy(INenUser other);
    static INenUser getFromPlayer(PlayerEntity player) {
        return player.getCapability(NENUSER, null).orElseThrow(() -> new IllegalArgumentException("NenUser must not be empty!"));
    }
    void nenpower1(PlayerEntity player);
    int getMaxCurrentNen();
    long getLastOpenedBook();
    void setLastOpenedBook(long last);
    boolean blockDamage();
    void setBlockDamage(boolean blockNext);
    boolean getNenActivated();
    void setNenActivated(boolean nenActivated);
    int getNenPower();
    float getCurrentNen();
    void setCurrentNen(float newNen);
    void setNencolor(int color);
    int getNenType();
    int getNencolor();
    void toggleGyo();
    void setGyo(boolean on);
    boolean getGyo();
    void toggleEn();
    void setEn(boolean on);
    boolean getEn();
    void toggleZetsu();
    void setZetsu(boolean on);
    boolean getZetsu();
    void toggleRen();
    void setRen(boolean on);
    boolean getRen();
    boolean getConjurerActivated();
    void setConjurerActivated(boolean oof);

    void enhancer1(PlayerEntity player);
    void manipulator1();
    void transmuter1(PlayerEntity player);
    void conjurer1(PlayerEntity player);
    void emitter1(PlayerEntity player);

    void enhancer2(PlayerEntity player);
    void manipulator2();
    void transmuter2(PlayerEntity player);
    void conjurer2(PlayerEntity player);
    void emitter2(PlayerEntity player);
}