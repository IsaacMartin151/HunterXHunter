package com.chubbychump.hunterxhunter.server.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import net.minecraft.entity.player.Player;

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
        if (player.getMainHandItemMainhand().getItem().isDamageable()) {
            int yeet = player.getMainHandItemMainhand().getDamage();
            player.getMainHandItemMainhand().setDamage(0);
        }
    }
}
