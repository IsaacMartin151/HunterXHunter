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
        this.nencolor = new int[] {0, 0, 1000};
    }

    @Override
    public int getNenType() {
        return 1;
    }
}
