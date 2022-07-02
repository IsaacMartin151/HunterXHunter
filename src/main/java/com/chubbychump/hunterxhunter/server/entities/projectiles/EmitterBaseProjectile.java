package com.chubbychump.hunterxhunter.server.entities.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmitterBaseProjectile extends DamagingProjectileEntity {
    private static final DataParameter<Integer> INFO = EntityDataManager.createKey(EmitterBaseProjectile.class, DataSerializers.VARINT);
    public static boolean inGround = false;
    private int type = -1;
    private int power = -1;

    public EmitterBaseProjectile(EntityType<? extends EmitterBaseProjectile> type, World p_i50166_2_) {
        super(type, p_i50166_2_);
    }

    public EmitterBaseProjectile(EntityType<? extends EmitterBaseProjectile> type, int n, int p, World p_i50166_2_) {
        this(type, p_i50166_2_);
        this.type = n;
        this.power = p;
    }

    @OnlyIn(Dist.CLIENT)
    public EmitterBaseProjectile(EntityType<? extends EmitterBaseProjectile> type, double x, double y, double z, double accelX, double accelY, double accelZ, World world) {
        super(type, x, y, z, accelX, accelY, accelZ, world);
    }

    public EmitterBaseProjectile(EntityType<? extends EmitterBaseProjectile> type, LivingEntity p_i50168_2_, double p_i50168_3_, double p_i50168_5_, double p_i50168_7_, World p_i50168_9_) {
        super(type, p_i50168_2_, p_i50168_3_, p_i50168_5_, p_i50168_7_, p_i50168_9_);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        super.setVelocity(x, y, z);
        //this.ticksInGround = 0;
    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = false;
        Vector3d vector3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        //if (this.arrowShake > 0) {
        //    --this.arrowShake;
        //}

        this.extinguish();

        if (this.inGround && !flag) {

        } else {
            Vector3d vector3d2 = this.getPositionVec();
            Vector3d vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vector3d3 = raytraceresult.getHitVec();
            }

            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.func_234616_v_();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onImpact(raytraceresult);
                    this.isAirBorne = true;
                }

                if (entityraytraceresult == null) {
                    break;
                }

                raytraceresult = null;
            }

            vector3d = this.getMotion();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            if (flag) {
                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
            } else {
                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            }

            this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
            this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    float f4 = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }
            }

            this.setMotion(vector3d.scale((double)f2));
            if (!this.hasNoGravity() && !flag) {
                Vector3d vector3d4 = this.getMotion();
                this.setMotion(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }
    }

    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        Vector3d yo = result.getHitVec();
        AreaEffectCloudEntity boom = new AreaEffectCloudEntity(world, yo.x, yo.y, yo.z);
        switch (this.type) {
            case 1:
                boom.addEffect(new EffectInstance(Effects.SLOWNESS, 600, power/4 + 1));
                boom.setPotion(Potions.SLOWNESS);
                break;
            case 2:
                boom.addEffect(new EffectInstance(Effects.POISON, 600, power/8 + 1));
                boom.setPotion(Potions.POISON);
                break;
            case 3:
                boom.addEffect(new EffectInstance(Effects.WEAKNESS, 600, power/4 + 1));
                boom.setPotion(Potions.WEAKNESS);
                break;
            case 4:
                boom.addEffect(new EffectInstance(Effects.SPEED, 600, power/4 + 1));
                boom.setPotion(Potions.SWIFTNESS);
                break;
            case 5:
                boom.addEffect(new EffectInstance(Effects.REGENERATION, 600, power/8 + 1));
                boom.setPotion(Potions.REGENERATION);
                break;
            case 6:
                boom.addEffect(new EffectInstance(Effects.STRENGTH, 600, power/4 + 1));
                boom.setPotion(Potions.STRENGTH);
                break;
            case 7:
                boom.addEffect(new EffectInstance(Effects.JUMP_BOOST, 600, power/5 + 1));
                boom.setPotion(Potions.LEAPING);
                break;
            case 8:
                world.createExplosion(this, DamageSource.MAGIC, null, yo.x, yo.y, yo.z,power/4 + 2,false, Explosion.Mode.BREAK);
                this.remove();
                break;
        }
        if (this.type != 8) {
            boom.setRadius(3);
            boom.setDuration(60);
            boom.setGlowing(true);
            world.addEntity(boom);
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerData() {
        this.getDataManager().register(INFO, 0);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        int info = this.getInfo();
        compound.putInt("info", info);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        int info = compound.getInt("info");
        this.setInfo(info);
    }

    public int getInfo() {
        return this.getDataManager().get(INFO);
    }

    public void setInfo(int info) {
        this.getDataManager().set(INFO, info);
    }
}
