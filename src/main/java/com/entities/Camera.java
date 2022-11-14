package com.entities;

import com.abilities.nenstuff.NenUser;
import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class Camera extends PathfinderMob {
    private static final EntityDataAccessor<Float> COUNTER = SynchedEntityData.defineId(Camera.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.defineId(Camera.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.defineId(Camera.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.defineId(Camera.class, EntityDataSerializers.FLOAT);
    public int amp = 5;
    public int timeStarted = 0;
    public ItemStack stack;
    public int testtype = 1;

    public Camera(EntityType<? extends PathfinderMob> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.stack = ItemStack.EMPTY;
        this.timeStarted = (int) Util.getMillis();
        this.setNoGravity(true);
        this.setInvulnerable(true);
//        this.lookControl = new CameraLookController(this);
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.noPhysics = true;
    }

    public Camera(EntityType<? extends PathfinderMob> entityTypeIn, Level worldIn, Player player, ItemStack istack) {
        this(entityTypeIn, worldIn);
        this.stack = istack;
        Vec3 target = new Vec3(player.getX(), player.getY()+1f, player.getZ());
        entityData.set(TARGET_POS_X, (float) player.getX());
        entityData.set(TARGET_POS_Y, (float) player.getY() + 1f);
        entityData.set(TARGET_POS_Z, (float) player.getZ());
        testtype = NenUser.getFromPlayer(player).getNenType();
        this.lookAt(EntityAnchorArgument.Anchor.EYES, target);
        this.lookControl.setLookAt(target.x, target.y, target.z, 100000, 100000);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.set(COUNTER, 0.00f);
        this.entityData.set(TARGET_POS_X, 0.0f);
        this.entityData.set(TARGET_POS_Y, 80.0f);
        this.entityData.set(TARGET_POS_Z, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        //HunterXHunter.LOGGER.info("ticking camera entity");
        if (entityData.get(COUNTER) > 2.00d) {
            if (stack != ItemStack.EMPTY) {
                HunterXHunter.LOGGER.info("Camera died, Dropping item "+stack.getDisplayName().getString());
                ItemEntity itementity = new ItemEntity(level, entityData.get(TARGET_POS_X), entityData.get(TARGET_POS_Y), entityData.get(TARGET_POS_Z), stack);
                level.addFreshEntity(itementity);
                if (itementity != null) {
                    itementity.setUnlimitedLifetime();
                }
            }
            if (this.level.isClientSide) {
                HunterXHunter.LOGGER.info("Setting render view back to player");
                Minecraft.getInstance().setCameraEntity(null);
                Minecraft.getInstance().setScreen(null);
                Minecraft.getInstance().setWindowActive(true);
            }
            else {
                HunterXHunter.LOGGER.info("killing off entity");

                this.kill();
            }
        }
        else {
            motion1();
        }
    }

    private void motion1() {
        double radians = entityData.get(COUNTER)*2*3.14;
        double newPosx = Math.cos(radians) * amp + entityData.get(TARGET_POS_X);
        double newPosy = amp * entityData.get(COUNTER) + entityData.get(TARGET_POS_Y) + 1;
        double newPosz = Math.sin(radians) * amp + entityData.get(TARGET_POS_Z);
        double dx = newPosx - this.getX();
        double dy = newPosy - this.getY();
        double dz =  newPosz- this.getZ();
        this.setDeltaMovement(dx, dy, dz);
        this.lookControl.setLookAt(entityData.get(TARGET_POS_X), entityData.get(TARGET_POS_Y), entityData.get(TARGET_POS_Z));

        entityData.set(COUNTER, entityData.get(COUNTER) + .01f);
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
