package com.chubbychump.hunterxhunter.server.entities.entityclasses;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.server.entities.controllers.CameraLookController;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class CameraEntity extends CreatureEntity {
    private static final DataParameter<Float> COUNTER = EntityDataManager.createKey(CameraEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> TARGET_POS_X = EntityDataManager.createKey(CameraEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> TARGET_POS_Y = EntityDataManager.createKey(CameraEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> TARGET_POS_Z = EntityDataManager.createKey(CameraEntity.class, DataSerializers.FLOAT);
    public int amp = 5;
    public int timeStarted = 0;
    public double y = 0.00d;
    public ItemStack stack;
    public int testtype = 1;

    public CameraEntity(EntityType<? extends CreatureEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.stack = ItemStack.EMPTY;
        this.timeStarted = (int) Util.milliTime();
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.lookController = new CameraLookController(this);
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.noClip = true;
    }

    public CameraEntity(EntityType<? extends CreatureEntity> entityTypeIn, World worldIn, PlayerEntity player, ItemStack istack) {
        this(entityTypeIn, worldIn);
        this.stack = istack;
        Vector3d target = new Vector3d(player.getPosX(), player.getPosY()+1f, player.getPosZ());
        dataManager.set(TARGET_POS_X, (float) player.getPosX());
        dataManager.set(TARGET_POS_Y, (float) player.getPosY() + 1f);
        dataManager.set(TARGET_POS_Z, (float) player.getPosZ());
        testtype = NenUser.getFromPlayer(player).getNenType();
        this.lookAt(EntityAnchorArgument.Type.EYES, target);
        this.lookController.setLookPosition(target.x, target.y, target.z, 100000, 100000);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(COUNTER, 0.00f);
        this.dataManager.register(TARGET_POS_X, 0.0f);
        this.dataManager.register(TARGET_POS_Y, 80.0f);
        this.dataManager.register(TARGET_POS_Z, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        //HunterXHunter.LOGGER.info("ticking camera entity");
        if (dataManager.get(COUNTER) > 2.00d) {
            if (stack != ItemStack.EMPTY) {
                LOGGER.info("Camera died, Dropping item "+stack.getDisplayName().getString());
                ItemEntity itementity = new ItemEntity(world, dataManager.get(TARGET_POS_X), dataManager.get(TARGET_POS_Y), dataManager.get(TARGET_POS_Z), stack);
                world.addEntity(itementity);
                if (itementity != null) {
                    itementity.setNoDespawn();
                }
            }
            if (this.world.isRemote()) {
                HunterXHunter.LOGGER.info("Setting render view back to player");
                Minecraft.getInstance().setRenderViewEntity(null);
                Minecraft.getInstance().displayGuiScreen(null);
                Minecraft.getInstance().setGameFocused(true);
            }
            else {
                HunterXHunter.LOGGER.info("killing off entity");

                this.setDead();
            }
        }
        else {
            motion1();
        }
    }

    private void motion1() {
        double radians = dataManager.get(COUNTER)*2*3.14;
        double newPosx = Math.cos(radians) * amp + dataManager.get(TARGET_POS_X);
        double newPosy = amp * dataManager.get(COUNTER) + dataManager.get(TARGET_POS_Y) + 1;
        double newPosz = Math.sin(radians) * amp + dataManager.get(TARGET_POS_Z);
        double dx = newPosx - this.getPosX();
        double dy = newPosy - this.getPosY();
        double dz =  newPosz- this.getPosZ();
        this.setMotion(dx, dy, dz);
        this.lookController.setLookPosition(dataManager.get(TARGET_POS_X), dataManager.get(TARGET_POS_Y), dataManager.get(TARGET_POS_Z));

        dataManager.set(COUNTER, dataManager.get(COUNTER) + .01f);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
