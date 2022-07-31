package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterDeathScreen;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterMainMenu;
import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.client.screens.AdvancementCustomToast;
import com.chubbychump.hunterxhunter.client.screens.NenEffectSelect;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncBookPacket;
import com.chubbychump.hunterxhunter.packets.SyncTransformCardPacket;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.server.entities.entityclasses.CameraEntity;
import com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic.AppearanceToggle;
import com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic.CAmongUs;
import com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic.CMiddleFinger;
import com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic.CObamiumPyramid;
import com.chubbychump.hunterxhunter.server.items.thehundred.tools.PhantomStaff;
import com.chubbychump.hunterxhunter.server.tileentities.TileEntityNenLight;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.tags.ITag;

import java.util.Collections;
import java.util.List;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;
import static com.chubbychump.hunterxhunter.client.core.handler.ClientInit.*;
import static com.chubbychump.hunterxhunter.client.gui.HUDHandler.drawSimpleManaHUD;
import static com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler.THEONEHUNDRED;
import static com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler.THEONEHUNDREDCARDS;
import static com.chubbychump.hunterxhunter.server.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;

//import net.minecraft.world.gen.placement.CountRangeConfig;

//@SuppressWarnings("ALL")
@Mod.EventBusSubscriber
public class EventsHandling {

    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerLevel){
            ServerLevel serverWorld = (ServerLevel)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().getGenerator()));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                LOGGER.error("Structure stuff didn't work");
            }
        }
    }

//    @SubscribeEvent
//    public static void biomeGeneration(BiomeLoadingEvent event) {
//        //event.getGeneration().getStructures().add(() -> CONFIGURED_WORLD_TREE);
//        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_AURA.get().withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, AURA_STONE.get().getDefaultState(), 4)));
//
////        if (event.getName().toString().equals(WORLD_TREE_BIOME.getId().toString())) {
////            LOGGER.info("adding feature to world tree biome");
////            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(WORLD_TREE.get().withConfiguration(new BaseWorldTreeConfiguration.Builder(
////                    new SimpleStateProvider(Blocks.OAK_LOG.getDefaultState()),
////                    new SimpleStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
////                    new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)),
////                    new WorldTreeTrunkPlacer(31, 23, 23),
////                    new TwoLayerFeature(0, 0, 0)).build()).withChance(10).feature);
////        }
//
//        //event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> GRAVITY_MINERALS_FEATURE_CONFIG);
//
//
//        if (event.getName().toString().equals(WORLD_TREE_BIOME.getId().toString())) {
//            LOGGER.info("adding feature to world tree biome");
//            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> WORLD_TREE_FEATURE_CONFIG);
//        }
//
//        if (event.getName().toString().equals(SPIDER_EAGLE_BIOME.getId().toString())) {
//            LOGGER.info("adding carver to spider eagle biome");
//            event.getGeneration().getCarvers(GenerationStage.Carving.AIR).add(() -> SPIDER_EAGLE_CARVER.get().func_242761_a(new ProbabilityConfig(.9f)));
//        }
//    }

    @SubscribeEvent
    public static void advancement(AdvancementEvent event) {
        //event.getPlayer().getGameProfile().getProperties().
        //TODO: get player skin and achievement description and show that to everyone
        //event.getPlayer().getServer().getPlayerList().getPlayerByUUID(asdf).container.
        AdvancementCustomToast customToast = new AdvancementCustomToast(event.getAdvancement(), event.getPlayer());
        Minecraft.getInstance().getToasts().addToast(customToast);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getLevel().isClientSide) {
            return;
        }

        // Fetch Capability
        Player player = event.getPlayer();
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
            CompoundTag data = player.getPersistentData();
            if (data.contains("levelHearts")) {

                // Miragte v1 Data
                CompoundTag tag = data.getCompound("levelHearts");
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
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "nenuser"), new NenProvider());
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "morehealth"), new MoreHealthProvider());
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "greedisland"), new GreedIslandProvider());
            LOGGER.info("Attached capability to player with ID# "+event.getObject().getId());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Fetch & Copy Capability
        Player playerOld = event.getOriginal();
        Player playerNew = event.getPlayer();
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
        if (event.getPlayer().getLevel().isClientSide) {
            return;
        }
        // Fetch Capability
        Player player = event.getPlayer();
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        INenUser nenCap = NenUser.getFromPlayer(player);


        // Handle "The End"
        if (event.isEndConquered()) {
            MoreHealth.updateClient((ServerPlayer) player, cap);
            NenUser.updateClient((ServerPlayer) player, nenCap);
            return;
        }

        // Handle Hardcore Mode
        if (Config.enableHardcore.get()) {

            // Send Message
            player.sendSystemMessage(Component.literal("text." + HunterXHunter.MOD_ID + ".hardcore"));

            // Reset Capability
            cap.setModifier(MoreHealth.getDefaultModifier());
            cap.setRampPosition((short) 0);
            cap.setHeartContainers((byte) 0);
            MoreHealth.updateClient((ServerPlayer) player, cap);
            NenUser.updateClient((ServerPlayer) player, nenCap);

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
            MoreHealth.updateClient((ServerPlayer) player, cap);
        }

        // Handle force lose xp on death
        if (Config.loseXpOnDeath.get()) {
            player.giveExperienceLevels(-player.experienceLevel - 1);
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
        NenUser.updateClient((ServerPlayer) player, nenCap);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        // Ensure server-side only
        if (event.getEntity().getLevel().isClientSide) {
            if (event.getSource() == DamageSource.FALL) {
                if (event.getEntity() instanceof Player) {
                    Player bruh = (Player) event.getEntity();
                    INenUser yo = NenUser.getFromPlayer(bruh);
                    if (yo.getNenType() == 1) {
                        if (yo.isBlockDamage()) {
                            //bruh.getLevel().playSound(bruh.getPosX(), bruh.getPosY(), bruh.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (event.getEntity().world.rand.nextFloat() - event.getEntity().world.rand.nextFloat()) * 0.2F) * 0.7F, false);
                        }
                    }
                }
            }
            return;
        }

        // Ensure player only
        Player player;
        if (!(event.getEntity() instanceof Player)) {
            return;
        } else {
            player = (Player) event.getEntity();
        }

        if(event.getSource() == DamageSource.FALL) {
            if (event.getEntity() instanceof Player) {
                INenUser yo = NenUser.getFromPlayer(player);
                if (yo.isBlockDamage()) {
                    event.setCanceled(true);
                    player.setInvulnerable(true);

                    //Explosion
                    //player.world.createExplosion(player, player.getPosX(), player.getPosY(), player.getPosZ(), (float) yo.getNenPower(), false, Explosion.Mode.DESTROY);
                    //EnhancerExplosion explosion = new EnhancerExplosion(player.getLevel(), player, player.getPosX(), player.getPosY(), player.getPosZ(), (float) yo.getNenPower(), false, Explosion.Mode.DESTROY);
                    //if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(player.getLevel(), explosion)) return explosion;
                    //explosion.doExplosionA();

                    //TODO: this can't be on client
                    //explosion.doExplosionB(true);

                    player.setInvulnerable(false);
                    yo.setBlockDamage(false);
                    NenUser.updateClient((ServerPlayer) player, yo);
                }
            }
        }

        // Check for OHKO
        if (Config.enableOhko.get()) {
            player.setHealth(0);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        //Clear bloodlust from the attacking entity (If there is one)
        if (event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource uh = (EntityDamageSource) event.getSource();
            Entity yuh = uh.getEntity();
            if (yuh instanceof Player) {
                ((Player) yuh).removeEffect(BLOODLUST_EFFECT.get());
                Minecraft.getInstance().player.removeEffect(BLOODLUST_EFFECT.get());
            }
        }

        // Ensure server-side only
        if (event.getEntity().getLevel().isClientSide) {
            return;
        }



        // Ensure player only
        Player player;
        if (!(event.getEntity() instanceof Player)) {
            if (event.getEntity() instanceof Bat) {
                event.getEntity().getLevel().addFreshEntity(new ItemEntity(event.getEntity().getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), BAT_WING.get().getDefaultInstance()));
            }
            return;
        } else {
            player = (Player) event.getEntity();
        }
        // Check for Keep Experience on Death

        if (Config.loseInvOnDeath.get()) {
            // Essentially, call Player#dropInventory, but ignoring the KEEP_INVENTORY gamerule
            try {
                ObfuscationReflectionHelper.findMethod(Player.class, "dropInventory").invoke(player);
                ObfuscationReflectionHelper.findMethod(Player.class, "destroyVanishingCursedItems").invoke(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.getInventory().dropAll();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // Ensure server-side only
        if (event.getPlayer().getLevel().isClientSide) {
            return;
        }
        // Fetch Capability
        Player player = event.getPlayer();
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
        if (Minecraft.getInstance().screen instanceof HunterXHunterMainMenu) {
            if (event.getSound().getSource() == SoundSource.MUSIC && !(event.getSound() instanceof MenuMusic)) {
                event.setSound(null);
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
            Minecraft.getInstance().pushGuiLayer(new HunterXHunterDeathScreen(Component.literal("Boi u ded"), false));
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
        if (event.getPlayer().getOffhandItem() instanceof AppearanceToggle) {
            Item held = event.getPlayer().getMainHandItemOffhand().getItem();
            if (held instanceof CAmongUs) {
                event.setCanceled(true);
                AmongUsRenderer mongus = new AmongUsRenderer(event.getRenderer().getRenderManager());
                mongus.render(new AmongUs(AMONG_US_ENTITY.get(), event.getPlayer().getLevel()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
            else if (held instanceof CObamiumPyramid) {
                event.setCanceled(true);
                ObamaRenderer bama = new ObamaRenderer(event.getRenderer().getRenderManager());
                bama.render(new Obama(OBAMA_ENTITY.get(), event.getPlayer().getLevel()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
            else if (held instanceof CMiddleFinger) {
                event.setCanceled(true);
                MiddleFingerRenderer mf = new MiddleFingerRenderer(event.getRenderer().getRenderManager());
                mf.render(new MiddleFinger(MIDDLE_FINGER_ENTITY.get(), event.getPlayer().getLevel()), event.getPlayer().rotationYaw, event.getPlayer().limbSwingAmount, event.getMatrixStack(), event.getBuffers(), 255);
                //held.renderNewAppearance();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderManipulator(TickEvent.RenderTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (event.renderTickTime % 5 == 0) {
            Minecraft bruh2 = Minecraft.getInstance();
            if (!bruh2.isGamePaused()) {
                if (player != null && player.isAlive()) {
                    INenUser yo = NenUser.getFromPlayer(player);
                    //event.setCanceled(true);
                    if (yo.getNenType() == 2) {
                        AxisAlignedBB box = new AxisAlignedBB(player.getPosX() - 20, player.getPosY() - 5, player.getPosZ() - 20, player.getPosX() + 20, player.getPosY() + 5, player.getPosZ() + 20);
                        List<AbstractClientPlayer> viewEntity = Minecraft.getInstance().world.getPlayers();
                        if (viewEntity.size() > 0) {
                            Minecraft.getInstance().setRenderViewEntity(viewEntity.get(yo.getManipulatorSelection() % viewEntity.size()));
                            return;
                        }
                    }
                    if (yo.getNenType() == 4 && yo.isRiftwalk()) {

                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            INenUser yo = NenUser.getFromPlayer(player);
            if (yo.getNenType() == 4) {
                if (yo.isRiftwalk()) {
                    LOGGER.info("Player is in riftwalk");
                    RenderSystem.enableBlend();
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableCull();

                    event.getMatrixStack().pushPose();
                    Matrix4f oof = event.getMatrixStack().getLast().pose();
                    ResourceLocation BUBBLES = new ResourceLocation(MOD_ID, "textures/gui/bubbles.png");
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder builder = tessellator.getBuffer();
                    builder.begin(GL_QUADS, POSITION_COLOR);
                    Minecraft.getInstance().getTextureManager().bindForSetup(BUBBLES);
                    RenderSystem.bindForSetup(Minecraft.getInstance().getTextureManager().getTexture(BUBBLES).getGlTextureId());
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



                    int[] bruh = yo.getRiftwalkPos();
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
                    event.getMatrixStack().popPose();
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
        if (!bruh.isPaused()) {
            Player player = Minecraft.getInstance().player;
            if (player.isAlive()) {
                INenUser yo = NenUser.getFromPlayer(player);
                if (event.getEntity() instanceof Player) {
                    INenUser ee = NenUser.getFromPlayer((Player) event.getEntity());
                    if (ee.isOpenedBook()) {
                        ObjectDrawingFunctions.BookRender(event.getPoseStack(), Util.getMillis() - ee.getLastOpenedBook(), (Player) event.getEntity());
                    }
                }
                boolean erm = yo.isGyo();
                if (erm && yo.getCurrentNen() > 0) {
                    showMobs(event.getPoseStack(), event.getMultiBufferSource(), event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        //LOGGER.info("Checking side. In livingupdate");
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getMainHandItemMainhand().getItem() instanceof PhantomStaff) {
                PhantomStaff staff = (PhantomStaff) player.getMainHandItemMainhand().getItem();

                if (staff.isOn()) {
                    player.noClip = true;
                    player.setNoGravity(true);
                    player.setMotion(new Vector3d(event.getEntity().getMotion().x, 0, event.getEntity().getMotion().z));
                    player.setPose(Pose.SWIMMING);
                    if (event.getEntity().ticksExisted % 40 == 0) {
                        player.getMainHandItemMainhand().damageItem(1, player, (player1) -> {
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
        if (event.player.isAlive() && event.phase == TickEvent.Phase.START && event.player.getLevel().isClientSide) {
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
                    PacketManager.sendToServer(new SyncBookPacket(event.player.getEntityId(), (CompoundTag) BOOK_CAPABILITY.writeNBT(oof, null)));
                }
                if (transformCard.isPressed()) {
                    ItemStack oof = event.player.getMainHandItemMainhand();
                    ITag<Item> bro = ItemTags.getCollection().get(THEONEHUNDRED);
                    ITag<Item> cards = ItemTags.getCollection().get(THEONEHUNDREDCARDS);
                    if (oof.getItem().isIn(ItemTags.getCollection().get(THEONEHUNDRED)) || oof.getItem().isIn(ItemTags.getCollection().get(THEONEHUNDREDCARDS))) {
                        if (!oof.isDamaged()) {
                            LOGGER.info("Item can be transformed into card");
                            PacketManager.sendToServer(new SyncTransformCardPacket(event.player.getEntityId(), new CompoundTag()));
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
//                    if (yo.getNenType() != 0) {
//                        Minecraft.getInstance().displayGuiScreen(PowerSelectScreen.instance);
//                    }
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
            if (event.getPlayer().getMainHandItemMainhand().getItem() == Items.SHEARS) {
                LOGGER.info("Player is holding shears");
                event.setNewSpeed(64f);
            }
            if (event.getPlayer().getMainHandItemMainhand().getItem() instanceof SwordItem) {
                LOGGER.info("Player is holding a sword");
                event.setNewSpeed(12f);
            }
        }
    }


    //DO NOT make this server only, because it gets called from the client
    @SubscribeEvent
    public static void calculateNenEffects(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && !event.player.getLevel().isClientSide && event.phase == TickEvent.Phase.START) {
            INenUser yo = NenUser.getFromPlayer(event.player);
            if (yo.getBurnout() > 0) {
                yo.setBurnout(yo.getBurnout() - 1);
            }
            int cost = 0;
            if (yo.isNenActivated()) {
                cost += 1;
            }
            if (yo.isGyo()) {
                cost += 1;
            }
            if (yo.isEn()) {
                cost += 1;
            }
            if (yo.isConjurerActivated()) {
                cost += 2;
            }
            if (yo.isRiftwalk()) {
                cost += 0; //TODO: this should be 3/4
            }
                // Set nen variable to "riftwalking", render player ghost at emerging location, use shader to grayscale the world
            yo.setCurrentNen(yo.getCurrentNen() - cost);
            if (yo.getCurrentNen() - cost < 0) {
                yo.setCurrentNen(0);
                if (yo.isRiftwalk()) {
                    // TODO: test this
                    int[] bruh = yo.getRiftwalkPos();
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
            NenUser.updateClient((ServerPlayer) event.player, yo);
        }
        if (event.player.isAlive() && event.player.ticksExisted % 10 == 0) {
            processLightPlacementForEntities(event.player.getLevel());
        }

        if (event.player.isAlive() && event.player.getLevel().isClientSide && event.phase == TickEvent.Phase.START) {
            if (event.player.getLevel().getBiome(event.player.getPosition()).getRegistryName().toString().equals(SPIDER_EAGLE_BIOME.getId().toString())) {
                LOGGER.info("time is "+event.player.getLevel().getGameTime());
                if (event.player.getLevel().getGameTime() % 900 == 800) {
                    event.player.playSound(WIND.get(), 1, 1);
                }
                if (event.player.getLevel().getGameTime() % 900 < 300 && !(event.player.isOnGround() && event.player.getPosY() > 64)) {
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
            Player player = mc.player;
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

    private static void processLightPlacementForEntities(Level theWorld) {
        for (Player player : Collections.unmodifiableList(theWorld.getPlayers())) {
            INenUser yo = NenUser.getFromPlayer(player);
            if (yo.isNenActivated() && yo.getCurrentNen() > 0) {
                BlockPos blockLocation = new BlockPos(MathHelper.floor(player.getPosX()), MathHelper.floor(player.getPosY() - 0.1D - player.getYOffset()), MathHelper.floor(player.getPosZ())).up();
                Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                Block lightBlockToPlace = NEN_LIGHT.get();
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(player, blockLocation, lightBlockToPlace);
                }
            }
            if (yo.isConjurerActivated() && yo.getCurrentNen() > 0) {
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
        player.level.setBlock(blockLocation, theLightBlock.defaultBlockState(), 3);
        TileEntityNenLight lightSource = (TileEntityNenLight) player.level.getBlockEntity(blockLocation);
        INenUser yo = NenUser.getFromPlayer((Player) player);
        int bro = yo.getNenPower();
        if(bro > 15) {
            bro = 15;
        }
        lightSource.setLevelOfLight(bro);
    }

    @OnlyIn(Dist.CLIENT)
    private static void showMobs(PoseStack matrixStack, MultiBufferSource buffer, LivingEntity entity) {
        if (entity instanceof Enemy) {
            int[] yuh = {500, 200, 0};
            ObjectDrawingFunctions.DrawSphere(matrixStack, yuh, 2);
        } else if (entity instanceof Player){
            int[] uh = {1000, 500, 0};
            ObjectDrawingFunctions.DrawSphere(matrixStack, uh, 2);
        }
    }


}