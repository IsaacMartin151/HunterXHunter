package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class NenUser {

    protected int nenPower;
    protected int currentNen;
    protected char control;
    protected boolean nenActivated;
    protected boolean gyo;
    protected boolean en;
    protected boolean ren;
    protected boolean zetsu;
    protected float[] nencolor;

    public void increaseNenPower(PlayerEntity theEntity) {
        return;
    }
    public void resetNen() {
        return;
    }
    public void toggleNen() {
        return;
    }
    public boolean isNenActivated() {
        return false;
    }
    public int getNenPower() {
        return 0;
    }
    public int getCurrentNen() {
        return 0;
    }
    public void setCurrentNen(int currentNen) {
        return;
    }
    public void toggleGyo() { return; }
    public boolean getGyo() {
        return false;
    }
    public float[] getNencolor() { return nencolor; }
    public void toggleEn() {
        return;
    }
    public boolean getEn() {
        return false;
    }
    public void toggleZetsu() {
        return;
    }
    public boolean getZetsu() {
        return false;
    }
    public void toggleRen() {
        return;
    }
    public boolean getRen() {
        return false;
    }
}