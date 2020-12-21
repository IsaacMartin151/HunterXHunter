package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.entity.player.PlayerEntity;

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
    public void nenpower1(PlayerEntity player) {
        if (player.getHeldItemMainhand().getItem().isDamageable()) {
            int yeet = player.getHeldItemMainhand().getDamage();
            player.getHeldItemMainhand().setDamage(0);
        }
    }
}
