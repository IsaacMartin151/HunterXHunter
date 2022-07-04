package com.chubbychump.hunterxhunter.server.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class EmitterBaseProjectile extends Projectile {
    public EmitterBaseProjectile(EntityType<? extends Projectile> p, Level l) {
        super(p, l);
    }

    @Override
    protected void defineSynchedData() {
        return;
    }
}
