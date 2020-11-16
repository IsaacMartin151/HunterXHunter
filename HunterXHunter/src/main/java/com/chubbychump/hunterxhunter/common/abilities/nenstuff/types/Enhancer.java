package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.OSU;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.WORLD_OF_ADVENTURES;

public class Enhancer extends NenUser {
    //private int amplifier
    public Enhancer() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.control = 'c';
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;

        this.ren = false;
        this.zetsu = false;
        this.nencolor = new float[] {1f, 1f, 1f};
    }

    public boolean isNenActivated() {
        return nenActivated;
    }

    public int getCurrentNen() {
        return currentNen;
    }

    public void increaseNenPower(PlayerEntity theEntity) {
        nenPower = nenPower + 15;
        HunterXHunter.LOGGER.info("User nen power has increased. Total is now " + nenPower);
        theEntity.playSound(WORLD_OF_ADVENTURES.get(), .5f, 1f);
    }

    public void setCurrentNen(int currentNen) {
        this.currentNen = currentNen;
    }

    public int getNenPower() {
        return nenPower;
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
    public void toggleGyo() {
        gyo = !gyo;
        HunterXHunter.LOGGER.info("Gyo toggled, is now "+gyo);
    }
    public float[] getNenColor() { return nencolor; }
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
