package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.WORLD_OF_ADVENTURES;

public class NenUser {
    protected int nenPower;
    protected int currentNen;
    protected char control;
    protected boolean nenActivated;
    protected boolean gyo;
    protected boolean en;
    protected boolean ren;
    protected boolean zetsu;
    protected int[] nencolor;

    public void increaseNenPower(PlayerEntity theEntity) {
        HunterXHunter.LOGGER.info("User nen power has increased. Total is now " + nenPower);
        theEntity.playSound(WORLD_OF_ADVENTURES.get(), .5f, 1f);
        nenPower += 1;
    }
    public void resetNen() {
        gyo = false;
        en = false;
        ren = false;
        zetsu = false;
    }

    public void toggleNen() {
        this.nenActivated = !this.nenActivated;
        if (nenActivated == false) {
            resetNen();
            HunterXHunter.LOGGER.info("Nen toggled, nen is now " + nenActivated);
        }
    }
    public boolean isNenActivated() {
        return false;
    }
    public int getNenPower() {
        return nenPower;
    }
    public int getCurrentNen() {
        return currentNen;
    }
    public void setCurrentNen(int currentNen) {
        this.currentNen = currentNen;
    }
    public void setNencolor(int[] color) {
        this.nencolor = color;
    }
    public int getNenType() {
        return 0;
    }
    public int[] getNencolor() { return nencolor; }

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
}