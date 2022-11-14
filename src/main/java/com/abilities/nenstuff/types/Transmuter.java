package com.abilities.nenstuff.types;

import com.abilities.nenstuff.NenUser;
import net.minecraft.world.entity.player.Player;


public class Transmuter extends NenUser {
    public Transmuter() {
        this.nenPower = 0;
        this.currentNen = 100;
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    @Override
    public int getNenType() {
        return 4;
    }

    @Override
    public void nenpower1(Player player) {

    }
}
