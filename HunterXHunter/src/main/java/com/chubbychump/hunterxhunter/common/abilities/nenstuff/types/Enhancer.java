package com.chubbychump.hunterxhunter.common.abilities.nenstuff.types;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class Enhancer extends NenUser {
    boolean attackSequenceOne = true;
    int powerDelay = 0;


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
    public void nenpower1(PlayerEntity player) {
        float setTo = getCurrentNen() - 50;
        if (setTo > 0) {
            setCurrentNen(setTo);
            setBurnout(60);
            attackSequenceOne = player.isOnGround();
            setBlockDamage(true);
            if (player.fallDistance > 0f) {
                attackSequenceOne = false;
            }
            if (attackSequenceOne) {
                //attackSequenceOne = !attackSequenceOne;
                player.addVelocity(0, 2, 0);
                //player.hurtResistantTime = 60;
                player.isInvulnerable();
                player.velocityChanged = true;
            } else {
                player.setMotion(0, 0, 0);
                Vector3d stare = player.getLookVec();
                //player.hurtResistantTime = 60;
                this.powerDelay = player.ticksExisted;
                stare.normalize();

                Vector3d move = new Vector3d(stare.x * 3, .2, stare.z * 3);
                player.addVelocity(move.x, move.y, move.z);
                player.velocityChanged = true;
            }
        }
    }
}
