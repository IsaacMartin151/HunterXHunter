package com.chubbychump.hunterxhunter.server.entities.projectiles;


import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;


import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.BASE_MAGIC_PROJECTILE;

public class ManipulatorTpProjectile extends Projectile {
    public ManipulatorTpProjectile(EntityType<? extends ManipulatorTpProjectile> p_i50153_1_, Level world) {
        super(p_i50153_1_, world);
    }

    public ManipulatorTpProjectile(Level worldIn, LivingEntity throwerIn) {
        super(BASE_MAGIC_PROJECTILE.get(), throwerIn, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ManipulatorTpProjectile(Level worldIn, double x, double y, double z) {
        super(BASE_MAGIC_PROJECTILE.get(), x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    @Override
    protected void onHitEntity(EntityHitResult ehr) {
        super.onHitEntity(ehr);
        ehr.getEntity().hurt(DamageSource.indirectMagic(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }

    @Override
    protected void defineSynchedData() {

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.remove(RemovalReason.UNLOADED_WITH_PLAYER);
        } else {
            super.tick();
        }

    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
