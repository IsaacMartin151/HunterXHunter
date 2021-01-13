package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.NenEffectSelect;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

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
    public boolean blockDamage() {
        return blockDamage;
    }

    @Override
    public void setBlockDamage(boolean blockNext) {
        this.blockDamage = blockNext;
    }

    @Override
    public void keybind1() {
        Minecraft.getInstance().displayGuiScreen(NenEffectSelect.instance);
    }
}
