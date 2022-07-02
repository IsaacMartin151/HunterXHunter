package com.chubbychump.hunterxhunter.server.entities.goals;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.Youpi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class LungeGoal extends Goal {
    private final Youpi leaper;
    private LivingEntity leapTarget;
    private final float leapMotionY;

    public LungeGoal(Youpi leapingEntity, float leapMotionYIn) {
        this.leaper = leapingEntity;
        this.leapMotionY = leapMotionYIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (leaper.ticksExisted > leaper.leapTimer) {
            this.leapTarget = this.leaper.getAttackTarget();
            if (this.leapTarget == null) {
                return false;
            } else {
                double d0 = this.leaper.getDistanceSq(this.leapTarget);
                if (d0 > 800.0D) {
                    if (!this.leaper.isOnGround() || !this.leapTarget.isOnGround()) {
                        return false;
                    } else {
                        leaper.leapTimer = leaper.ticksExisted + 500;
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return !this.leaper.isOnGround();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        if (leapTarget.isOnGround()) {
            Vector3d vector3d = this.leaper.getMotion();
            Vector3d vector3d1 = new Vector3d(this.leapTarget.getPosX() - this.leaper.getPosX(), 0.0D, this.leapTarget.getPosZ() - this.leaper.getPosZ());
            double extraY = this.leapTarget.getPosY() - this.leaper.getPosY();
            this.leaper.setMotion(vector3d1.x/7, (double) 1.5f + (extraY/7), vector3d1.z/7 );
        }
    }
}

