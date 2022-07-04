package com.chubbychump.hunterxhunter.server.entities.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;

public class LungeGoal extends LeapAtTargetGoal {
    public LungeGoal(Mob m, float yDir) {
        super(m, yDir);
    }
}

