package com.chubbychump.hunterxhunter.common.entities.controllers;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class CameraLookController extends LookController {
    float lastTargetPitch = 0f;
    float lastTargetYawHead = 0f;

    public CameraLookController(MobEntity mob) {
        super(mob);
    }

    public void setLookPosition(Vector3d lookVector) {
        this.setLookPosition(lookVector.x, lookVector.y, lookVector.z);
    }

    /**
     * Sets position to look at using entity
     */
    public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
        this.setLookPosition(entityIn.getPosX(), getEyePosition(entityIn), entityIn.getPosZ(), deltaYaw, deltaPitch);
    }

    public void setLookPosition(double x, double y, double z) {
        this.setLookPosition(x, y, z, (float)this.mob.getFaceRotSpeed(), (float)this.mob.getVerticalFaceSpeed());
    }

    /**
     * Sets position to look at
     */
    public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.deltaLookYaw = deltaYaw;
        this.deltaLookPitch = deltaPitch;
        this.isLooking = true;
    }

    /**
     * Updates look
     */
    @Override
    public void tick() {
        float yawHead = this.clampedRotate(this.mob.rotationYawHead, this.getTargetYaw(), 9999999);
        float pitch = this.clampedRotate(this.mob.rotationPitch, this.getTargetPitch(), 999999);
        lastTargetPitch = pitch;
        lastTargetYawHead = yawHead;
        this.mob.rotationYawHead = yawHead;
        this.mob.rotationYaw = yawHead;
        this.mob.rotationPitch = pitch;
        //
    }

    protected float getTargetPitch() {
        double d0 = this.posX - this.mob.getPosX();
        double d1 = this.posY - this.mob.getPosYEye();
        double d2 = this.posZ - this.mob.getPosZ();
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        return (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
    }

    protected float getTargetYaw() {
        double d0 = this.posX - this.mob.getPosX();
        double d1 = this.posZ - this.mob.getPosZ();
        return (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    /**
     * Rotate as much as possible from {@code from} to {@code to} within the bounds of {@code maxDelta}
     */
    protected float clampedRotate(float from, float to, float maxDelta) {
        float f = MathHelper.wrapSubtractDegrees(from, to);
        float f1 = MathHelper.clamp(f, -maxDelta, maxDelta);
        return from + f1;
    }

    private static double getEyePosition(Entity entity) {
        return entity instanceof LivingEntity ? entity.getPosYEye() : (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D;
    }
}
