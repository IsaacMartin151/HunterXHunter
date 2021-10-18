package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterDeathScreen;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterMainMenu;
import com.chubbychump.hunterxhunter.client.screens.NenEffectSelect;
import com.chubbychump.hunterxhunter.client.screens.PowerSelectScreen;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.AmongUs;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.CameraEntity;
import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.MiddleFinger;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.Obama;
import com.chubbychump.hunterxhunter.common.entities.renderers.AmongUsRenderer;
import com.chubbychump.hunterxhunter.common.entities.renderers.MiddleFingerRenderer;
import com.chubbychump.hunterxhunter.common.entities.renderers.ObamaRenderer;
import com.chubbychump.hunterxhunter.common.generation.BaseWorldTreeFeatureConfig;
import com.chubbychump.hunterxhunter.common.generation.WorldTreeTrunkPlacer;
import com.chubbychump.hunterxhunter.common.items.ItemVariants;
import com.chubbychump.hunterxhunter.common.items.thehundred.cosmetic.AppearanceToggle;
import com.chubbychump.hunterxhunter.common.items.thehundred.cosmetic.CAmongUs;
import com.chubbychump.hunterxhunter.common.items.thehundred.cosmetic.CMiddleFinger;
import com.chubbychump.hunterxhunter.common.items.thehundred.cosmetic.CObamiumPyramid;
import com.chubbychump.hunterxhunter.common.items.thehundred.tools.PhantomStaff;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncBookPacket;
import com.chubbychump.hunterxhunter.packets.SyncTransformCardPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.profiler.IProfiler;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chubbychump.hunterxhunter.HunterXHunter.*;
import static com.chubbychump.hunterxhunter.client.core.handler.ClientProxy.*;
import static com.chubbychump.hunterxhunter.client.gui.HUDHandler.drawSimpleManaHUD;
import static com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions.isaacCube;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler.THEONEHUNDRED;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler.THEONEHUNDREDCARDS;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR_TEX;
import static org.lwjgl.opengl.GL11.GL_QUADS;

//import net.minecraft.world.gen.placement.CountRangeConfig;

//@SuppressWarnings("ALL")
@Mod.EventBusSubscriber
public class EventsHandling {

    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                LOGGER.error("Structure stuff didn't work");
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<Structure<?>, StructureSeparationSettings> tempMap = serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_;
            tempMap.putIfAbsent(WORLD_TREE_STRUCTURE.get(), DimensionStructuresSettings.field_236191_b_.get(WORLD_TREE_STRUCTURE.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }

    @SubscribeEvent
    public static void biomeGeneration(BiomeLoadingEvent event) {
        //event.getGeneration().getStructures().add(() -> CONFIGURED_WORLD_TREE);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_AURA.get().withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, AURA_STONE.get().getDefaultState(), 4)));

//        if (event.getName().toString().equals(WORLD_TREE_BIOME.getId().toString())) {
//            LOGGER.info("adding feature to world tree biome");
//            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(WORLD_TREE.get().withConfiguration(new BaseWorldTreeFeatureConfig.Builder(
//                    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
//                    new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
//                    new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)),
//                    new WorldTreeTrunkPlacer(31, 23, 23),
//                    new TwoLayerFeature(0, 0, 0)).build()).withChance(10).feature);
//        }

        event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> GRAVITY_MINERALS_FEATURE_CONFIG);
        if (event.getName().toString().equals(WORLD_TREE_BIOME.getId().toString())) {
            LOGGER.info("adding feature to world tree biome");
            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> WORLD_TREE_FEATURE_CONFIG);
        }

        if (event.getName().toString().equals(SPIDER_EAGLE_BIOME.getId().toString())) {
            LOGGER.info("adding carver to spider eagle biome");
            event.getGeneration().getCarvers(GenerationStage.Carving.AIR).add(() -> SPIDER_EAGLE_CARVER.get().func_242761_a(new ProbabilityConfig(.9f)));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getEntityWorld().isRemote) {
            return;
        }

        // Fetch Capability
        PlayerEntity player = event.getPlayer();
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        INenUser yo = NenUser.getFromPlayer(player);
        ItemStackHandler itemHandler = player.getCapability(BOOK_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("Book can not be null"));

        int Type = yo.getNenType();
        // Apply Health Modifier
        if (Type == 1) {
            HunterXHunter.applyHealthModifier(player, cap.getEnhancerModifier());
        }
        else {
            HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
        }

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
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "nenuser"), new NenProvider());
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "morehealth"), new MoreHealthProvider());
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "greedisland"), new GreedIslandProvider());
            LOGGER.info("Attached capability to player with ID# "+event.getObject().getUniqueID());
        }
    }

    @SubscribeEvent
    public static void registerStructures(RegistryEvent.Register<Structure<?>> event)
    {
        //RegistryHandler.setupStructures();
//        Registry.registerConfiguredStructures();
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Fetch & Copy Capability
        PlayerEntity playerOld = event.getOriginal();
        PlayerEntity playerNew = event.getPlayer();
        IMoreHealth capOld = MoreHealth.getFromPlayer(playerOld);
        IMoreHealth capNew = MoreHealth.getFromPlayer(playerNew);
        INenUser nenOld = NenUser.getFromPlayer(playerOld);
        INenUser nenNew = NenUser.getFromPlayer(playerNew);
        ItemStackHandler bookOld = playerOld.getCapability(BOOK_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("Book can not be null"));
        ItemStackHandler bookNew = playerNew.getCapability(BOOK_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("Book can not be null"));
        bookNew.deserializeNBT(bookOld.serializeNBT());
        nenNew.copy(nenOld);
        capNew.copy(capOld);

        int Type = nenNew.getNenType();
        // Copy Health on Dimension Change
        if (!event.isWasDeath()) {
            if (Type == 1) {
                HunterXHunter.applyHealthModifier(playerNew, capNew.getEnhancerModifier());
            }
            else {
                HunterXHunter.applyHealthModifier(playerNew, capNew.getTrueModifier());
            }
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
        INenUser nenCap = NenUser.getFromPlayer(player);


        // Handle "The End"
        if (event.isEndConquered()) {
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            NenUser.updateClient((ServerPlayerEntity) player, nenCap);
            return;
        }

        // Handle Hardcore Mode
        if (Config.enableHardcore.get()) {

            // Send Message
            player.sendMessage(new TranslationTextComponent("text." + HunterXHunter.MOD_ID + ".hardcore"), Util.DUMMY_UUID);

            // Reset Capability
            cap.setModifier(MoreHealth.getDefaultModifier());
            cap.setRampPosition((short) 0);
            cap.setHeartContainers((byte) 0);
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            NenUser.updateClient((ServerPlayerEntity) player, nenCap);

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

                }
            } else {
                cap.setModifier(newModifier);
                cap.setRampPosition((short) (newModifier / 2));
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
        //TODO: maybe recalc nen? HunterXHunter.recalcPlayerNen(player, player.experienceLevel);
        INenUser yo = NenUser.getFromPlayer(player);
        int Type = yo.getNenType();
        if (Type == 1) {
            HunterXHunter.applyHealthModifier(player, cap.getEnhancerModifier());
        }
        else {
            HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
        }
        player.setHealth(player.getMaxHealth());
        yo.setCurrentNen(yo.getMaxCurrentNen());
        NenUser.updateClient((ServerPlayerEntity) player, nenCap);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        // Ensure server-side only
        if (event.getEntity().getEntityWorld().isRemote) {
            if (event.getSource() == DamageSource.FALL) {
                if (event.getEntity() instanceof PlayerEntity) {
                    PlayerEntity bruh = (PlayerEntity) event.getEntity();
                    INenUser yo = NenUser.getFromPlayer(bruh);
                    if (yo.getNenType() == 1) {
                        if (yo.blockDamage()) {
                            //bruh.getEntityWorld().playSound(bruh.getPosX(), bruh.getPosY(), bruh.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (event.getEntity().world.rand.nextFloat() - event.getEntity().world.rand.nextFloat()) * 0.2F) * 0.7F, false);
                        }
                    }
                }
            }
            return;
        }

        // Ensure player only
        PlayerEntity player;
        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        } else {
            player = (PlayerEntity) event.getEntity();
        }

        if(event.getSource() == DamageSource.FALL) {
            if (event.getEntity() instanceof PlayerEntity) {
                INenUser yo = NenUser.getFromPlayer(player);
                if (yo.blockDamage()) {
                    event.setCanceled(true);
                    player.setInvulnerable(true);

                    //Explosion
                    //player.world.createExplosion(player, player.getPosX(), player.getPosY(), player.getPosZ(), (float) yo.getNenPower(), false, Explosion.Mode.DESTROY);
                    //EnhancerExplosion explosion = new EnhancerExplosion(player.getEntityWorld(), player, player.getPosX(), player.getPosY(), player.getPosZ(), (float) yo.getNenPower(), false, Explosion.Mode.DESTROY);
                    //if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(player.getEntityWorld(), explosion)) return explosion;
                    //explosion.doExplosionA();

                    //TODO: this can't be on client
                    //explosion.doExplosionB(true);

                    player.setInvulnerable(false);
                    yo.setBlockDamage(false);
                    NenUser.updateClient((ServerPlayerEntity) player, yo);
                }
            }
        }

        // Check for OHKO
        if (Config.enableOhko.get()) {
            player.setHealth(0);
        }
    }

    @SubscribeEvent
    public static void onVillagerTrade(VillagerTradesEvent event) {
        // Ensure server-side only
        if (event.getType() == MASADORIAN.get()) {
            event.getTrades().get(1).add(new RandomTradeBuilder(64, 10, 0.05F).setEmeraldPrice(1).setForSale(Items.NETHER_STAR, 1, 3).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(64, 10, 0.05F).setEmeraldPrice(1).setForSale(Items.ACACIA_FENCE, 1, 3).build());
            //TODO: add all the spell cards
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        //Clear bloodlust from the attacking entity (If there is one)
        if (event.getSource() instanceof EntityDamageSource ) {
            EntityDamageSource uh = (EntityDamageSource) event.getSource();
            Entity yuh = uh.getTrueSource();
            if (yuh instanceof PlayerEntity) {
                ((PlayerEntity) yuh).removeActivePotionEffect(BLOODLUST_EFFECT.get());
                Minecraft.getInstance().player.removeActivePotionEffect(BLOODLUST_EFFECT.get());
            }
        }

        // Ensure server-side only
        if (event.getEntity().getEntityWorld().isRemote) {
            return;
        }



        // Ensure player only
        PlayerEntity player;
        if (!(event.getEntity() instanceof PlayerEntity)) {
            if (event.getEntity() instanceof BatEntity) {
                event.getEntity().getEntityWorld().addEntity(new ItemEntity(event.getEntity().getEntityWorld(), event.getEntity().getPosition().getX(), event.getEntity().getPosition().getY(), event.getEntity().getPosition().getZ(), BAT_WING.get().getDefaultInstance()));
            }
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
        INenUser yo = NenUser.getFromPlayer(player);
        int Type = yo.getNenType();
        // Re-add health modifier
        if (Type == 1) {
            HunterXHunter.applyHealthModifier(player, cap.getEnhancerModifier());
        }
        else {
            HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
        }
        // Synchronize
        cap.synchronise(player);
        //nenCap.synchronise(player);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void playSoundEvent(PlaySoundEvent event) {
        if (Minecraft.getInstance().currentScreen instanceof MainMenuScreen) {
            if (event.getSound().getCategory() == SoundCategory.MUSIC && !(event.getSound() instanceof MenuMusic)) {
                event.setResultSound(null);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setGUI(GuiScreenEvent.InitGuiEvent event) {
        if (event.getGui().getClass() == (MainMenuScreen.class)) {
            Minecraft.getInstance().displayGuiScreen(new HunterXHunterMainMenu());
        }
        if (event.getGui().getClass() == (DeathScreen.class)) {
            Minecraft.getInstance().displayGuiScreen(new HunterXHunterDeathScreen(ITextComponent.getTextComponentOrEmpty("Boi u ded"), false));
        }
     }

     @OnlyIn(Dist.CLIENT)
     @SubscribeEvent
     public static void worldLast(RenderHandEvent event) {
        if (Minecraft.getInstance().getRenderViewEntity() instanceof CameraEntity) {
            event.setCanceled(true);
        }
     }

     public static void potionAdded(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getPotion() == BLOODLUST_EFFECT.get()) {
            Minecraft.getInstance().player.playSound(AMOOGUS.get(), 1, 1);
        }
     }

//    @SubscribeEvent
//    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
//        itemVariants = new ItemVariants();
//        itemVariants.setRegistryName("hunterxhunter_item_variants_registry_name");
//        itemRegisterEvent.getRegistry().register(itemVariants);
//    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderPlayer(RenderPlayerEvent event) {
        if (event.getPlayer().getHeldItemOffhand().getItem() instanceof AppearanceToggle) {
            Item held = event.getPlayer().getHeldItemOffhand().getItem();
            if (held instanceof CAmongUs) {
                event.setCanceled(true);
                AmongUsRenderer mongus = new AmongUsRenderer(event.getRenderer().getRenderManager());
                mongus.render(new AmongUs(AMONG_US_ENTITY.get(), event.getPlayer().getEntityWorld()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
            else if (held instanceof CObamiumPyramid) {
                event.setCanceled(true);
                ObamaRenderer bama = new ObamaRenderer(event.getRenderer().getRenderManager());
                bama.render(new Obama(OBAMA_ENTITY.get(), event.getPlayer().getEntityWorld()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
            else if (held instanceof CMiddleFinger) {
                event.setCanceled(true);
                MiddleFingerRenderer mf = new MiddleFingerRenderer(event.getRenderer().getRenderManager());
                mf.render(new MiddleFinger(MIDDLE_FINGER_ENTITY.get(), event.getPlayer().getEntityWorld()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderManipulator(TickEvent.RenderTickEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (event.renderTickTime % 5 == 0) {
            Minecraft bruh2 = Minecraft.getInstance();
            if (!bruh2.isGamePaused()) {
                if (player != null && player.isAlive()) {
                    INenUser yo = NenUser.getFromPlayer(player);
                    //event.setCanceled(true);
                    if (yo.getNenType() == 2) {
                        AxisAlignedBB box = new AxisAlignedBB(player.getPosX() - 20, player.getPosY() - 5, player.getPosZ() - 20, player.getPosX() + 20, player.getPosY() + 5, player.getPosZ() + 20);
                        List<AbstractClientPlayerEntity> viewEntity = Minecraft.getInstance().world.getPlayers();
                        if (viewEntity.size() > 0) {
                            Minecraft.getInstance().setRenderViewEntity(viewEntity.get(yo.getManipulatorSelection() % viewEntity.size()));
                            return;
                        }
                    }
                    if (yo.getNenType() == 4 && yo.getBoolRiftWalk()) {

                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            INenUser yo = NenUser.getFromPlayer(player);
            if (yo.getNenType() == 4) {
                if (yo.getBoolRiftWalk()) {
                    LOGGER.info("Player is in riftwalk");
                    RenderSystem.enableBlend();
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableCull();

                    event.getMatrixStack().push();
                    Matrix4f oof = event.getMatrixStack().getLast().getMatrix();
                    ResourceLocation BUBBLES = new ResourceLocation(MOD_ID, "textures/gui/bubbles.png");
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder builder = tessellator.getBuffer();
                    builder.begin(GL_QUADS, POSITION_COLOR);
                    Minecraft.getInstance().getTextureManager().bindTexture(BUBBLES);
                    RenderSystem.bindTexture(Minecraft.getInstance().getTextureManager().getTexture(BUBBLES).getGlTextureId());
                    float r = .6f;
                    float g = .0f;
                    float b = .0f;
                    float a = .7f;

                    builder.pos(oof, -1f, -1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, -1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, 1f, -1f).color(r, g, b, a).endVertex();

                    builder.pos(oof, -1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, 1f, 1f).color(r, g, b, a).endVertex();

                    builder.pos(oof, 1f, -1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, -1f).color(r, g, b, a).endVertex();

                    builder.pos(oof, -1f, -1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, 1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, 1f, -1f).color(r, g, b, a).endVertex();

                    builder.pos(oof, -1f, 1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, 1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, 1f, -1f).color(r, g, b, a).endVertex();

                    builder.pos(oof, -1f, -1f, -1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,-1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, -1f, 1f).color(r, g, b, a).endVertex();
                    builder.pos(oof,1f, -1f, -1f).color(r, g, b, a).endVertex();



                    int[] bruh = yo.getRiftWalk();
                    BlockPos yee = player.getPosition();
                    int[] oo = new int[3];
                    oo[0] = 8 * (yee.getX() - bruh[0]) + bruh[0];
                    oo[1] = 8 * (yee.getY() - bruh[1]) + bruh[1];
                    oo[2] = 8 * (yee.getZ() - bruh[2]) + bruh[2];
                    //LOGGER.info("Setting player position to "+oo[0]+", "+oo[1]+", "+oo[2]);
                    LOGGER.info("In riftwalk, rendering box where player will end up");
                    //player.setPosition(oo[0], oo[1], oo[2]);
                    int x = oo[0];
                    int y = oo[1];
                    int z = oo[2];

                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y, z+1).color(1f, 1f, 1f, 1f).endVertex();

                    builder.pos(x, y+2, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y+2, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();

                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y+2, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y, z+1).color(1f, 1f, 1f, 1f).endVertex();

                    builder.pos(x+1, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y+2, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y, z+1).color(1f, 1f, 1f, 1f).endVertex();

                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y, z).color(1f, 1f, 1f, 1f).endVertex();

                    builder.pos(x, y, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x+1, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();
                    builder.pos(x, y+2, z+1).color(1f, 1f, 1f, 1f).endVertex();

                    tessellator.draw();
                    event.getMatrixStack().pop();
                }
                else {
                    //ShaderHelper.releaseShader();

                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderEvent(RenderLivingEvent.Post event) { //RenderWorldLastEvent for drawing stuff
        Minecraft bruh = Minecraft.getInstance();
        if (!bruh.isGamePaused()) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.isAlive()) {
                INenUser yo = NenUser.getFromPlayer(player);
                if (event.getEntity() instanceof PlayerEntity) {
                    INenUser ee = NenUser.getFromPlayer((PlayerEntity) event.getEntity());
                    if (ee.openedBook()) {
                        ObjectDrawingFunctions.BookRender(event.getMatrixStack(), Util.milliTime() - ee.getLastOpenedBook(), (PlayerEntity) event.getEntity());
                    }
                }
                boolean erm = yo.getGyo();
                if (erm && yo.getCurrentNen() > 0) {
                    showMobs(event.getMatrixStack(), event.getBuffers(), event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        //LOGGER.info("Checking side. In livingupdate");
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player.getHeldItemMainhand().getItem() instanceof PhantomStaff) {
                PhantomStaff staff = (PhantomStaff) player.getHeldItemMainhand().getItem();

                if (staff.isOn()) {
                    player.noClip = true;
                    player.setNoGravity(true);
                    player.setMotion(new Vector3d(event.getEntity().getMotion().x, 0, event.getEntity().getMotion().z));
                    player.setPose(Pose.SWIMMING);
                    if (event.getEntity().ticksExisted % 40 == 0) {
                        player.getHeldItemMainhand().damageItem(1, player, (player1) -> {
                            player1.sendBreakAnimation(Hand.MAIN_HAND);
                        });
                    }
                }
                else {
                    //player.fallDistance = 0;
                    player.noClip = false;
                    player.setNoGravity(false);
                    player.setPose(Pose.STANDING);
                }
            }
            else {
                //player.fallDistance = 0;
                player.noClip = false;
                player.setNoGravity(false);
                player.setPose(Pose.STANDING);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void keyPress(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().isRemote()) {
            INenUser yo = NenUser.getFromPlayer(event.player);
            boolean updatePlayer = false;
            if (increaseNen.isPressed()) {
                yo.increaseNenPower();
                updatePlayer = true;
            }
            if (yo.getNenPower() > 0) {
                if (book.isPressed()) {
                    updatePlayer = true;
                    yo.setOpenedBook(true);
                    yo.setLastOpenedBook(Util.milliTime());
                    LOGGER.info("pressed book button");
                    Minecraft.getInstance().player.playSound(OPEN_BOOK.get(), 1, 1);
                    BookItemStackHandler oof = (BookItemStackHandler) event.player.getCapability(BOOK_CAPABILITY).orElseThrow(null);
                    PacketManager.sendToServer(new SyncBookPacket(event.player.getEntityId(), (CompoundNBT) BOOK_CAPABILITY.writeNBT(oof, null)));
                }
                if (transformCard.isPressed()) {
                    ItemStack oof = event.player.getHeldItemMainhand();
                    ITag<Item> bro = ItemTags.getCollection().get(THEONEHUNDRED);
                    ITag<Item> cards = ItemTags.getCollection().get(THEONEHUNDREDCARDS);
                    if (oof.getItem().isIn(ItemTags.getCollection().get(THEONEHUNDRED)) || oof.getItem().isIn(ItemTags.getCollection().get(THEONEHUNDREDCARDS))) {
                        if (!oof.isDamaged()) {
                            LOGGER.info("Item can be transformed into card");
                            PacketManager.sendToServer(new SyncTransformCardPacket(event.player.getEntityId(), new CompoundNBT()));
                        }
                    }
                }
                if (nenControl.isPressed() && yo.getCurrentNen() > 0f) {
                    yo.toggleNen();
                    updatePlayer = true;
                }
                if (gyo.isPressed() && yo.getCurrentNen() > 0f) {
                    yo.toggleGyo();
                    updatePlayer = true;
                }
                if (nenPower1.isPressed()) {
                    LOGGER.info("Type is " + yo.getNenType());
                    switch (yo.getNenType()) {
                        case 0:
                            break;
                        case 1:
                            yo.enhancer1(event.player);
                            break;
                        case 2:
                            yo.manipulator1(event.player);
                            yo.setEntityID(-2);
                            break;
                        case 3:
                            yo.transmuter1(event.player);
                            break;
                        case 4:
                            yo.conjurer1(event.player);
                            break;
                        case 5:
                            yo.emitter1(event.player);
                            break;
                    }
                    updatePlayer = true;
                }
                if (nenPower2.isPressed()) {
                    if (yo.getNenType() != 0) {
                        Minecraft.getInstance().displayGuiScreen(PowerSelectScreen.instance);
                    }
                }
                if (devTesting.isPressed()) {
                    Minecraft.getInstance().displayGuiScreen(NenEffectSelect.instance);
                }

                if (updatePlayer == true) {
                    NenUser.updateServer(event.player, yo);
                }
            }
        }
    }


    @SubscribeEvent
    public static void potionexpire(PotionEvent.PotionExpiryEvent event) {
        LOGGER.info("Potion expiring");
        if (event.getPotionEffect().getPotion() == BLOODLUST_EFFECT.get() ) {
            LOGGER.info("Expiring potion is bloodlust, killing player");
            event.getEntity().attackEntityFrom(DamageSource.STARVE, 100);
        }
    }

    @SubscribeEvent
    public static void potionremove(PotionEvent.PotionRemoveEvent event) {
        LOGGER.info("Trying to remove potion");
        if (event.getPotionEffect().getPotion() == BLOODLUST_EFFECT.get()) {
            LOGGER.info("It's an instance of bloodlust, cancelling removal");
        }
    }

    @SubscribeEvent
    public static void breakingBlock(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() == SUPER_COBWEB.get()) {
            LOGGER.info("It's an instance of super cobweb");
            if (event.getPlayer().getHeldItemMainhand().getItem() == Items.SHEARS) {
                LOGGER.info("Player is holding shears");
                event.setNewSpeed(64f);
            }
            if (event.getPlayer().getHeldItemMainhand().getItem() instanceof SwordItem) {
                LOGGER.info("Player is holding a sword");
                event.setNewSpeed(12f);
            }
        }
    }


    //DO NOT make this server only, because it gets called from the client
    @SubscribeEvent
    public static void calculateNenEffects(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && !event.player.getEntityWorld().isRemote && event.phase == TickEvent.Phase.START) {
            INenUser yo = NenUser.getFromPlayer(event.player);
            if (yo.getBurnout() > 0) {
                yo.setBurnout(yo.getBurnout() - 1);
            }
            int cost = 0;
            if (yo.getNenActivated()) {
                cost += 1;
            }
            if (yo.getGyo()) {
                cost += 1;
            }
            if (yo.getEn()) {
                cost += 1;
            }
            if (yo.getConjurerActivated()) {
                cost += 2;
            }
            if (yo.getBoolRiftWalk()) {
                cost += 0; //TODO: this should be 3/4
            }
                // Set nen variable to "riftwalking", render player ghost at emerging location, use shader to grayscale the world
            yo.setCurrentNen(yo.getCurrentNen() - cost);
            if (yo.getCurrentNen() - cost < 0) {
                yo.setCurrentNen(0);
                if (yo.getBoolRiftWalk()) {
                    // TODO: test this
                    int[] bruh = yo.getRiftWalk();
                    BlockPos yee = event.player.getPosition();
                    int[] oo = new int[3];
                    oo[0] = 8 * (yee.getX() - bruh[0]) + bruh[0];
                    oo[1] = 8 * (yee.getY() - bruh[1]) + bruh[1];
                    oo[2] = 8 * (yee.getZ() - bruh[2]) + bruh[2];
                    LOGGER.info("Setting player position to "+oo[0]+", "+oo[1]+", "+oo[2]);
                    event.player.setPosition(oo[0], oo[1], oo[2]);
                }
                yo.resetNen();
            }
            if (cost > 0) {
                yo.setBurnout(60);
            }
            if (yo.getBurnout() <= 0) {
                yo.setCurrentNen(yo.getCurrentNen() + .3f);
            }
            if (yo.getCurrentNen() > yo.getMaxCurrentNen()) {
                yo.setCurrentNen(yo.getMaxCurrentNen());
            }
            NenUser.updateClient((ServerPlayerEntity) event.player, yo);
        }
        if (event.player.isAlive() && event.player.ticksExisted % 10 == 0) {
            processLightPlacementForEntities(event.player.getEntityWorld());
        }

        if (event.player.isAlive() && event.player.getEntityWorld().isRemote() && event.phase == TickEvent.Phase.START) {
            if (event.player.getEntityWorld().getBiome(event.player.getPosition()).getRegistryName().toString().equals(SPIDER_EAGLE_BIOME.getId().toString())) {
                LOGGER.info("time is "+event.player.getEntityWorld().getGameTime());
                if (event.player.getEntityWorld().getGameTime() % 900 == 800) {
                    event.player.playSound(WIND.get(), 1, 1);
                }
                if (event.player.getEntityWorld().getGameTime() % 900 < 300 && !(event.player.isOnGround() && event.player.getPosY() > 64)) {
                    LOGGER.info("adding motion");
                    event.player.addVelocity(0, Math.max(.1 * (80 - event.player.getPosY()) / 16, 0), 0);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        IProfiler profiler = mc.getProfiler();
        MatrixStack ms = event.getMatrixStack();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            profiler.startSection("nen-hud");
            profiler.startSection("manaBar");
            PlayerEntity player = mc.player;
            if (!player.isSpectator() && player.isAlive()) {
                INenUser yo = NenUser.getFromPlayer(player);
                boolean anyRequest = (yo.getNenPower() > 0);
                float totalMana = yo.getCurrentNen();
                int totalMaxMana = yo.getMaxCurrentNen();
                //Successfully synced capabilities up, this is the render thread
                if (anyRequest) {
                    drawSimpleManaHUD(ms, 0x4444FF, (int) totalMana, totalMaxMana, "Nen");
                }
            }
            profiler.endStartSection("itemsRemaining");
            profiler.endSection();

            profiler.endSection();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
        }
    }

    private static void processLightPlacementForEntities(World theWorld) {
        for (PlayerEntity player : Collections.unmodifiableList(theWorld.getPlayers())) {
            INenUser yo = NenUser.getFromPlayer(player);
            if (yo.getNenActivated() && yo.getCurrentNen() > 0) {
                BlockPos blockLocation = new BlockPos(MathHelper.floor(player.getPosX()), MathHelper.floor(player.getPosY() - 0.1D - player.getYOffset()), MathHelper.floor(player.getPosZ())).up();
                Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                Block lightBlockToPlace = NEN_LIGHT.get();
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(player, blockLocation, lightBlockToPlace);
                }
            }
            if (yo.getConjurerActivated() && yo.getCurrentNen() > 0) {
                Block conjurerBlock = CONJURER_BLOCK.get();
                for (int i = 0; i < 9; i++) {
                    BlockPos blockLocation = new BlockPos(MathHelper.floor(player.getPosX() + (i%3) - 1), MathHelper.floor(player.getPosY() - 2.4D - player.getYOffset()), MathHelper.floor(player.getPosZ()) + (i/3 - 1)).up();
                    Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                    if (blockAtLocation == Blocks.AIR) {
                        player.world.setBlockState(blockLocation, conjurerBlock.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    private static void placeLightSourceBlock(Entity player, BlockPos blockLocation, Block theLightBlock) {
        player.world.setBlockState(blockLocation, theLightBlock.getDefaultState(), 3);
        TileEntityNenLight lightSource = (TileEntityNenLight) player.world.getTileEntity(blockLocation);
        INenUser yo = NenUser.getFromPlayer((PlayerEntity) player);
        int bro = yo.getNenPower();
        if(bro > 15) {
            bro = 15;
        }
        lightSource.setLevelOfLight(bro);
    }

    @OnlyIn(Dist.CLIENT)
    private static void showMobs(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity) {
        if (entity instanceof IMob) {
            int[] yuh = {500, 200, 0};
            ObjectDrawingFunctions.DrawSphere(matrixStack, yuh, 2);
        } else if (entity instanceof VillagerEntity){
            int[] yuh = {500, 200, 0};
            ObjectDrawingFunctions.DrawDragon(matrixStack, yuh);
        } else if (entity instanceof PlayerEntity){
            int[] uh = {1000, 500, 0};
            ObjectDrawingFunctions.DrawSphere(matrixStack, uh, 2);
        }
    }


}