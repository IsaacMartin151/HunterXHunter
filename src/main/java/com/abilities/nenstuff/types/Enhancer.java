package com.abilities.nenstuff.types;

import com.abilities.nenstuff.NenUser;
import com.screens.AbilitySelect;
import net.minecraft.client.Minecraft;

public class Enhancer extends NenUser {
    boolean attackSequenceOne = true;

    public Enhancer() {
        this.nenActivated = false;
        this.gyo = false;
        this.en = false;
        this.ren = false;
        this.zetsu = false;
    }

    @Override
    public int getNenType() {
        return 1;
    }

    @Override
    public boolean isBlockDamage() {
        return blockDamage;
    }

    @Override
    public void setBlockDamage(boolean blockNext) {
        this.blockDamage = blockNext;
    }

    @Override
    public void keybind1() {
        Minecraft.getInstance().setScreen(new AbilitySelect());
    }
}
