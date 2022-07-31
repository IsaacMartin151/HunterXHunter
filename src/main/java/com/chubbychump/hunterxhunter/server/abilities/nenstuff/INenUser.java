package com.chubbychump.hunterxhunter.server.abilities.nenstuff;

import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER_CAPABILITY;

public interface INenUser extends INBTSerializable<CompoundTag> {
    boolean isNenActivated();
    int getNenPower();
    float getCurrentNen();
    int getNenType();
    int getNenColor();
    boolean isGyo();
    boolean isEn();
    boolean isZetsu();
    boolean isRen();
    boolean isBlockDamage();

    void setNenActivated(boolean nenActivated);
    void setNenPower(int power);
    void setCurrentNen(float currentNen);
    void setNenType(int nenType);
    void setNenColor(int nenColor);
    void setGyo(boolean gyo);
    void setEn(boolean en);
    void setZetsu(boolean zetsu);
    void setRen(boolean ren);
    void setBlockDamage(boolean blockDamage);

    void toggleNen();
    void toggleGyo();
    void toggleEn();
    void toggleZetsu();
    void toggleRen();

    boolean isOpenedBook();
    void setOpenedBook(boolean openedBook);
    int getBurnout();
    boolean isOverlay();
    void setBurnout(int burn);
    void setClipping(boolean clipping);
    boolean isClipping();
    int getPassivePower();
    void setPassivePower(int i);
    int getManipulatorSelection();
    void setManipulatorSelection(int manipulatorSelection);
    void setRiftwalk(boolean riftwalk);
    boolean isRiftwalk();
    boolean isConjurerActivated();
    void setConjurerActivated(boolean conjurerActivated);

    void keybind1();
    void increaseNenPower();
    void resetNen();

    int getFailCounter();
    void setFailCounter(int failCounter);
    void copy(INenUser other);
    void nenpower1(Player player);
    int getMaxCurrentNen();
    long getLastOpenedBook();
    void setLastOpenedBook(long lastOpenedBook);

    void setRiftwalkPos(int[] i);
    int[] getRiftwalkPos();


    void setEntityID(int e);
    int getEntityID();

    void enhancer1(Player player);
    void manipulator1(Player player);
    void transmuter1(Player player);
    void conjurer1(Player player);
    void emitter1(Player player);
}