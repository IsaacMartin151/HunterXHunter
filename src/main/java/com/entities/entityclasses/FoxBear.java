package com.entities.entityclasses;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;

public class FoxBear extends PolarBear {
    public FoxBear(EntityType<? extends FoxBear> type, Level worldIn) {
        super(type, worldIn);
        this.setCustomName(Component.literal("Foxbear"));
    }
}
