package com.chubbychump.hunterxhunter.server.entities.entityclasses;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;

public class FoxBear extends PolarBear {
    public FoxBear(EntityType<? extends FoxBear> type, Level LevelIn) {
        super(type, LevelIn);
        this.setCustomName(Component.literal("Foxbear"));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 2.5F;
    }

}
