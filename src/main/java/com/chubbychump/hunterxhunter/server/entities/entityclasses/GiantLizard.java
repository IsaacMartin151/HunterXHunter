package com.chubbychump.hunterxhunter.server.entities.entityclasses;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;

public class GiantLizard extends Rabbit {
    public GiantLizard(EntityType<? extends GiantLizard> p_i50247_1_, Level p_i50247_2_) {
        super(p_i50247_1_, p_i50247_2_);
        this.jumpControl = new RabbitJumpControl(this);
    }

}
