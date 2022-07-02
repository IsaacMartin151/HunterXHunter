package com.chubbychump.hunterxhunter.server.entities.entityclasses;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class FoxBear extends PolarBearEntity {
    public FoxBear(EntityType<? extends FoxBear> type, World worldIn) {
        super(type, worldIn);
        this.setCustomName(ITextComponent.getTextComponentOrEmpty("Foxbear"));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 2.5F;
    }

}
