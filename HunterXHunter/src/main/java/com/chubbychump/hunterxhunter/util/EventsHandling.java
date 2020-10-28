package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.client.rendering.RenderTypeLine;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModEntityTypes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.HunterXHunter.*;
import static com.chubbychump.hunterxhunter.init.ModBlocks.NENLIGHT;

@Mod.EventBusSubscriber
public class EventsHandling {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getEntityWorld().isRemote) {
            return;
        }

        // Fetch Capability
        PlayerEntity player = event.getPlayer();
        IMoreHealth cap = MoreHealth.getFromPlayer(player);

        // Apply Health Modifier
        HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());

        // Initial Setup
        if (cap.getVersion() == 1) {
            // Search for Old Data
            CompoundNBT data = player.getEntity().getPersistentData();
            if (data.contains("levelHearts")) {

                // Miragte v1 Data
                CompoundNBT tag = data.getCompound("levelHearts");
                cap.setModifier((float) tag.getDouble("modifier"));
                cap.setRampPosition(tag.getByte("levelRampPosition"));
                cap.setHeartContainers(tag.getByte("heartContainers"));

                // Apply new health modifier
                HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());

                // Remove Old Data
                data.remove("levelHearts");
            } else {
                //
            }

            // Set health to max & up version
            player.setHealth(player.getMaxHealth());
            cap.setVersion((byte) 2);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "morehealth"), new MoreHealthProvider());
        }
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "nenuser"), new NenProvider());
            LOGGER.info("Attached capability to player with ID# "+event.getObject().getUniqueID());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Fetch & Copy Capability
        PlayerEntity playerOld = event.getOriginal();
        PlayerEntity playerNew = event.getPlayer();
        IMoreHealth capOld = MoreHealth.getFromPlayer(playerOld);
        IMoreHealth capNew = MoreHealth.getFromPlayer(playerNew);
        capNew.copy(capOld);

        // Copy Health on Dimension Change
        if (!event.isWasDeath()) {
            HunterXHunter.applyHealthModifier(playerNew, capNew.getTrueModifier());
            playerNew.setHealth(playerOld.getHealth());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getEntityWorld().isRemote) {
            return;
        }

        // Fetch Capability
        PlayerEntity player = event.getPlayer();
        IMoreHealth cap = MoreHealth.getFromPlayer(player);

        // Handle "The End"
        if (event.isEndConquered()) {
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            return;
        }

        // Handle Hardcore Mode
        if (Config.enableHardcore.get()) {

            // Send Message
            player.sendMessage(new TranslationTextComponent("text." + HunterXHunter.MOD_ID + ".hardcore"), Util.field_240973_b_);

            // Reset Capability
            cap.setModifier(MoreHealth.getDefaultModifier());
            cap.setRampPosition((short) 0);
            cap.setHeartContainers((byte) 0);
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);

            // Handle Punishing the Player
        } else if (Config.punishAmount.get() > 0) {

            int amount = Config.punishAmount.get() * 2; // The amount of health to remove
            float oldModifier = cap.getModifier(); // The old modifier before having removed the amount
            float newModifier = oldModifier - amount; // The new modifier after having removed the amount

            // If the new modifier is less than the default modifier, set to default & remove the necessary heart containers
            // Otherwise, the newModifier is from experience, so we can just use that
            if (newModifier < MoreHealth.getDefaultModifier()) {
                float healthLeft = (newModifier - MoreHealth.getDefaultModifier()); // The amount of health left to remove after setting to default (should be negative)
                byte containers = cap.getHeartContainers(); // How many heart containers before having removed the health
                byte containersLeft = (byte) Math.max((containers + (healthLeft / 2)), 0); // How many heart containers the player still has after removing the health (min 0)

                // Set Data
                cap.setModifier(MoreHealth.getDefaultModifier());
                cap.setRampPosition((short) 0);
                cap.setHeartContainers(containersLeft);

                // Send the Message
                float xpHeartsLost = oldModifier / 2; // Health -> Hearts
                byte containersLost = (byte) (containers - containersLeft); // Calculate Containers Lost
                int heartsLost = (int) xpHeartsLost + containersLost;

                // Send the Message
                if (heartsLost > 0) {
                    player.sendMessage(new TranslationTextComponent("text." + HunterXHunter.MOD_ID + ".punish", heartsLost), Util.field_240973_b_);
                }
            } else {
                cap.setModifier(newModifier);
                cap.setRampPosition((short) (newModifier / 2));

                // Send the Message
                player.sendMessage(new TranslationTextComponent("text." + HunterXHunter.MOD_ID + ".punish", (amount / 2)), Util.field_240973_b_);
            }

            // Notify Client of Capability Changes
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
        }

        // Handle force lose xp on death
        if (Config.loseXpOnDeath.get()) {
            player.addExperienceLevel(-player.experienceLevel - 1);
        }

        // Re-add health modifier and fill health
        HunterXHunter.recalcPlayerHealth(player, player.experienceLevel);
        HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
        player.setHealth(player.getMaxHealth());
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        // Ensure server-side only
        if (event.getEntity().getEntityWorld().isRemote) {
            return;
        }

        // Ensure player only
        PlayerEntity player;
        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        } else {
            player = (PlayerEntity) event.getEntity();
        }

        // Check for OHKO
        if (Config.enableOhko.get()) {
            player.setHealth(0);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        // Ensure server-side only
        if (event.getEntity().getEntityWorld().isRemote) {
            return;
        }

        // Ensure player only
        PlayerEntity player;
        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        } else {
            player = (PlayerEntity) event.getEntity();
        }

        // Check for Keep Experience on Death
        if (Config.loseInvOnDeath.get()) {
            // Essentially, call PlayerEntity#dropInventory, but ignoring the KEEP_INVENTORY gamerule
            try {
                ObfuscationReflectionHelper.findMethod(PlayerEntity.class, "dropInventory").invoke(player);
                ObfuscationReflectionHelper.findMethod(PlayerEntity.class, "destroyVanishingCursedItems").invoke(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.inventory.dropAllItems();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getEntityWorld().isRemote) {
            return;
        }

        // Fetch Capability
        PlayerEntity player = event.getPlayer();
        IMoreHealth cap = MoreHealth.getFromPlayer(player);

        // Re-add health modifier
        HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());

        // Synchronize
        cap.synchronise(player);
    }

    @SubscribeEvent
    public static void onPlayerLevelUp(PlayerXpEvent.LevelChange event) {
        // Ensure server-side only
        if (event.getPlayer().getEntityWorld().isRemote) {
            return;
        }
        // Fetch player and recalc player's health
        PlayerEntity player = event.getPlayer();
        HunterXHunter.recalcPlayerHealth(player, player.experienceLevel + event.getLevels());
    }

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerXpEvent.PickupXp event) {
        event.getOrb().xpValue *= Config.xpMultiplier.get();
    }

    @SubscribeEvent
    public static void renderEvent(RenderLivingEvent.Post event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        LazyOptional<NenUser> yo = player.getCapability(NenProvider.MANA_CAP, null);
        boolean erm = yo.orElseThrow(null).getGyo();
        if (erm) {
            showMobs(event.getMatrixStack(), event.getBuffers(), event.getEntity());
        }

    }

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event) {
        if (Config.loseXpOnDeath.get() && (event.getEntity() instanceof PlayerEntity)) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            // See PlayerEntity experience drop code
            int i = player.experienceLevel * 7;
            event.setDroppedExperience(Math.min(i, 100));
        }
    }

    @SubscribeEvent
    public static void keyPress(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive()) {
            if (nenControl.isPressed()) {
                LazyOptional<NenUser> yo = event.player.getCapability(NenProvider.MANA_CAP, null);
                yo.orElseThrow(null).toggleNen();
                boolean erm = yo.orElseThrow(null).isNenActivated();
            }
            if (increaseNen.isPressed()) {
                LazyOptional<NenUser> yo = event.player.getCapability(NenProvider.MANA_CAP, null);
                yo.orElseThrow(null).increaseNenPower();
            }
            if (gyo.isPressed()) {
                LazyOptional<NenUser> yo = event.player.getCapability(NenProvider.MANA_CAP, null);
                yo.orElseThrow(null).toggleGyo();
            }
            if (nenPower1.isPressed()) {
                    World world = event.player.world;
                    PlayerEntity player = event.player;
                    BlockPos pos = event.player.func_233580_cy_();

                    //if (!world.isRemote && player != null /*&& ManaItemHandler.instance().requestManaExactForTool(stack, player, COST, false)*/) {
                        LOGGER.info("Laser beam! BlockPos is " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
                        Entity entity = ModEntityTypes.RAYBEAM.create(world);
                        entity.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        world.addEntity(entity);

                        //player.getCooldownTracker().setCooldown(this, IManaProficiencyArmor.hasProficiency(player, stack) ? COOLDOWN / 2 : COOLDOWN);
                        //ManaItemHandler.instance().requestManaExactForTool(stack, player, COST, true);

                        event.player.getEntityWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_BLAZE_AMBIENT, player != null ? SoundCategory.PLAYERS : SoundCategory.BLOCKS, 1F, 1F);
                    //}
            }
            processLightPlacementForEntities(event.player.getEntityWorld());
        }
    }

    private static void processLightPlacementForEntities(World theWorld) {
        for (Entity theEntity : Collections.unmodifiableList(theWorld.getPlayers())) {
            if (theEntity.getCapability(NenProvider.MANA_CAP).orElseThrow(null).isNenActivated()) {
                BlockPos blockLocation = new BlockPos(MathHelper.floor(theEntity.getPosX()), MathHelper.floor(theEntity.getPosY() - 0.1D - theEntity.getYOffset()), MathHelper.floor(theEntity.getPosZ())).up();
                Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                NenLight lightBlockToPlace = NENLIGHT;
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace);
                }
            }
        }
    }
    private static void placeLightSourceBlock(Entity theEntity, BlockPos blockLocation, NenLight theLightBlock) {
        theEntity.world.setBlockState(blockLocation, theLightBlock.getDefaultState(), 3);
        TileEntityNenLight lightSource = new TileEntityNenLight();
        LazyOptional<NenUser> yo = theEntity.getCapability(NenProvider.MANA_CAP, null);
        int bro = yo.orElse(null).getNenPower();
        if(bro > 15) {
            bro = 15;
        }
        lightSource.setLevelOfLight(bro);
        theEntity.world.setTileEntity(blockLocation, lightSource);
    }

    private static void greenLine(IVertexBuilder builder, Matrix4f positionMatrix, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {
        builder.pos(positionMatrix, dx1, dy1, dz1)
                .color(0.0f, 1.0f, 0.0f, 1.0f)
                .endVertex();
        builder.pos(positionMatrix, dx2, dy2, dz2)
                .color(0.0f, 1.0f, 0.0f, 1.0f)
                .endVertex();
    }

    private static void yellowLine(IVertexBuilder builder, Matrix4f positionMatrix, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {
        builder.pos(positionMatrix, dx1, dy1, dz1)
                .color(1.0f, 1.0f, 0.0f, 1.0f)
                .endVertex();
        builder.pos(positionMatrix, dx2, dy2, dz2)
                .color(1.0f, 1.0f, 0.0f, 1.0f)
                .endVertex();
    }

    private static void redLine(IVertexBuilder builder, Matrix4f positionMatrix, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {
        builder.pos(positionMatrix, dx1, dy1, dz1)
                .color(1.0f, 0.0f, 0.0f, 1.0f)
                .endVertex();
        builder.pos(positionMatrix, dx2, dy2, dz2)
                .color(1.0f, 0.0f, 0.0f, 1.0f)
                .endVertex();
    }

    private static void showMobs(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity) {
        IVertexBuilder builder = buffer.getBuffer(RenderTypeLine.OVERLAY_LINES);
        Matrix4f positionMatrix = matrixStack.getLast().getMatrix();
        AxisAlignedBB box = entity.getBoundingBox();
        if (entity instanceof IMob) {
            redLine(builder, positionMatrix, 0f, 0f, 0f, 1, 0, 0);
            redLine(builder, positionMatrix, 0, 1f, 0, 1, 1, 0);
            redLine(builder, positionMatrix, 0f, 0f, 1f, 1, 0, 1);
            redLine(builder, positionMatrix, 0, .5f, 0, 0, 6, 0);

            redLine(builder, positionMatrix, 0f, 0f, 0f, 0, 0, 1);
            redLine(builder, positionMatrix, 1f, 0f, 0, 1, 0, 1);
            redLine(builder, positionMatrix, 0f, 0f, 1f, 1, 0, 1);
            redLine(builder, positionMatrix, 0, .5f, 0, 0, 6, 0);

            redLine(builder, positionMatrix, 0f, 0f, 0f, 1, 0, 0);
            redLine(builder, positionMatrix, 0, 1f, 0, 1, 1, 0);
            redLine(builder, positionMatrix, 0f, 0f, 1f, 1, 0, 1);
            redLine(builder, positionMatrix, 0, .5f, 0, 0, 6, 0);
        } else if (entity instanceof PlayerEntity){
            ObjectDrawingFunctions.DrawSphere(builder, matrixStack, entity);
        } else {
            //greenLine(builder, positionMatrix, 0, 0f, -1f * (float) (box.maxZ-box.minZ), 0, 0, (float) (box.maxZ-box.minZ));
            //greenLine(builder, positionMatrix, 0, 0f, 0, 0, (float) (box.maxY-box.minY), 0);
            //greenLine(builder, positionMatrix, -1f * (float) (box.maxX-box.minX), 0f, 0, (float) (box.maxX-box.minX), 0, 0);
            ObjectDrawingFunctions.DrawSphere(builder, matrixStack, entity);
            //greenLine(builder, positionMatrix, 0, .5f, 0, 0, 6, 0);
        }
    }
}