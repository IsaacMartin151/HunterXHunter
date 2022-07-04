package com.chubbychump.hunterxhunter.server.entities.controllers;


import com.mojang.math.Vector3d;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class CameraLookController extends LookControl {
    float lastTargetPitch = 0f;
    float lastTargetYawHead = 0f;

    public CameraLookController(Mob mob) {
        super(mob);
    }

    public void setLookPosition(Vector3d lookVector) {
        this.setLookPosition(lookVector.x, lookVector.y, lookVector.z);
    }

    /**
     * Sets position to look at using entity
     */
    public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
        this.setLookPosition(entityIn.getX(), getEyePosition(entityIn), entityIn.getZ(), deltaYaw, deltaPitch);
    }

    public void setLookPosition(double x, double y, double z) {
        this.setLookPosition(x, y, z, (float)this.mob.getHeadRotSpeed(), (float)this.mob.getHeadRotSpeed());
    }

    public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.lastTargetYawHead = deltaYaw;
        this.lastTargetPitch = deltaPitch;
        this.lookAtCooldown = 5;
    }

    @Override
    public void tick() {
        float yawHead = this.clampedRotate(this.mob.yRotO, this.getTargetYaw(), 9999999);
        float pitch = this.clampedRotate(this.mob.xRotO, this.getTargetPitch(), 999999);
        lastTargetPitch = pitch;
        lastTargetYawHead = yawHead;
        this.mob.yHeadRot = yawHead;
        this.mob.yBodyRot = yawHead;
        this.mob.xRotO = pitch;
        //
    }

    protected float getTargetPitch() {
        double d0 = this.getWantedX() - this.mob.getX();
        double d1 = this.getWantedY() - this.mob.getEyeY();
        double d2 = this.getWantedZ() - this.mob.getZ();
        double d3 = (double) Math.sqrt(d0 * d0 + d2 * d2);
        return (float)(-(Math.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
    }

    protected float getTargetYaw() {
        double d0 = this.getWantedX() - this.mob.getX();
        double d1 = this.getWantedZ() - this.mob.getZ();
        return (float)(Math.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    protected float clampedRotate(float from, float to, float maxDelta) {
        float f = from - to;
        float f1 = 0;
        if (f > maxDelta) {
            f1 = maxDelta;
        }
        else if (f < -maxDelta) {
            f1 = -maxDelta;
        }

        return from + f1;
    }

    private static double getEyePosition(Entity entity) {
        return entity instanceof LivingEntity ? entity.getEyeY() : (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D;
    }
}
