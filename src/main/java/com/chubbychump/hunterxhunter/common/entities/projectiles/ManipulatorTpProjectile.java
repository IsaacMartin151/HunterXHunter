package com.chubbychump.hunterxhunter.common.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.BASE_MAGIC_PROJECTILE;

public class ManipulatorTpProjectile extends ProjectileItemEntity {
    public ManipulatorTpProjectile(EntityType<? extends ManipulatorTpProjectile> p_i50153_1_, World world) {
        super(p_i50153_1_, world);
    }

    public ManipulatorTpProjectile(World worldIn, LivingEntity throwerIn) {
        super(BASE_MAGIC_PROJECTILE.get(), throwerIn, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ManipulatorTpProjectile(World worldIn, double x, double y, double z) {
        super(BASE_MAGIC_PROJECTILE.get(), x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        p_213868_1_.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0F);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.func_234616_v_();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.remove();
        } else {
            super.tick();
        }

    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
