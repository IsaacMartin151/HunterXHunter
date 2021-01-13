package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class Manipulator extends NenUser {
    public Manipulator() {
        this.nenPower = 1;
        this.currentNen = 100;
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
        this.passivePower = 0;
    }

    @Override
    public int getNenType() {
        return 3;
    }
}
