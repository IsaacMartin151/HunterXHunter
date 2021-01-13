package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterDeathScreen;
import com.chubbychump.hunterxhunter.client.gui.HunterXHunterMainMenu;
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
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.types.Enhancer;
import com.chubbychump.hunterxhunter.common.blocks.ConjurerBlock;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.client.gui.NenEffectSelect;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityConjurerBlock;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModBlocks;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncBookPacket;
import com.chubbychump.hunterxhunter.packets.SyncTransformCardPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.criterion.ItemDurabilityTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.profiler.IProfiler;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static com.chubbychump.hunterxhunter.client.core.handler.ClientProxy.*;
import static com.chubbychump.hunterxhunter.client.gui.HUDHandler.drawSimpleManaHUD;
import static com.chubbychump.hunterxhunter.client.gui.HunterXHunterDeathScreen.bruhh;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler.THEONEHUNDRED;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.init.ModBlocks.NENLIGHT;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

//import net.minecraft.world.gen.placement.CountRangeConfig;

//@SuppressWarnings("ALL")
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
    public static void generateOres(FMLLoadCompleteEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.NETHER) {

            }
            else if (biome.getCategory() == Biome.Category.THEEND) {

            }
            else {
                genOre(biome, 5, 8, 5, 50, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, NEN_LIGHT.get().getDefaultState(), 2);
            }
        }
    }

    private static void genOre(Biome biome, int count, int bottomOffset, int topOffset, int max, RuleTest filler, BlockState defaultBlockstate, int size) {
        //CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        //RuleTest
        //OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        //ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        //biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
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
    public static void renderManipulator(TickEvent.RenderTickEvent event) { //RenderWorldLastEvent for drawing stuff
        Minecraft bruh = Minecraft.getInstance();
        if (!bruh.isGamePaused()) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player != null && player.isAlive()) {
                INenUser yo = NenUser.getFromPlayer(player);
                if (yo.getOverlay()) {
                    Minecraft.getInstance().setRenderViewEntity(Minecraft.getInstance().world.getPlayers().get(yo.getPassivePower()));
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void keyPress(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().isRemote()) {
            INenUser yo = NenUser.getFromPlayer(event.player);
            boolean updatePlayer = false;
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
                if (oof.getItem().isIn(ItemTags.getCollection().get(THEONEHUNDRED))) {
                    PacketManager.sendToServer(new SyncTransformCardPacket(event.player.getEntityId(), (CompoundNBT) NENUSER.writeNBT(yo, null)));
                }
            }
            if (nenControl.isPressed()) {
                yo.toggleNen();
                updatePlayer = true;
            }
            if (increaseNen.isPressed()) {
                yo.increaseNenPower();
                updatePlayer = true;
            }
            if (gyo.isPressed()) {
                yo.toggleGyo();
                updatePlayer = true;
            }
            if (nenPower1.isPressed()) {
                yo.keybind1();
            }
            if (nenPower2.isPressed()) {
                LOGGER.info("Type is "+yo.getNenType());
                switch (yo.getNenType()) {
                    case 0:
                        LOGGER.info("enhancer");
                        break;
                    case 1:
                        yo.enhancer1(event.player);
                        LOGGER.info("enhancer");
                        break;
                    case 2:
                        yo.manipulator1();
                        LOGGER.info("manipulator");
                        break;
                    case 3:
                        yo.transmuter1(event.player);
                        LOGGER.info("transmuter");
                        break;
                    case 4:
                        yo.conjurer1(event.player);
                        LOGGER.info("conjurer");
                        break;
                    case 5:
                        yo.emitter1(event.player);
                        LOGGER.info("emitter");
                        break;
                }
                updatePlayer = true;
            }
            if (updatePlayer == true) {
                LOGGER.info("conjureractivated is "+yo.getConjurerActivated());
                NenUser.updateServer(event.player, yo);
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
            yo.setCurrentNen(yo.getCurrentNen() - cost);
            if (yo.getCurrentNen() - cost < 0) {
                yo.setCurrentNen(0);
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
                NenLight lightBlockToPlace = NENLIGHT;
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(player, blockLocation, lightBlockToPlace);
                }
            }
            if (yo.getConjurerActivated() && yo.getCurrentNen() > 0) {
                ConjurerBlock conjurerBlock = ModBlocks.CONJURER_BLOCK;
                for (int i = 0; i < 9; i++) {
                    BlockPos blockLocation = new BlockPos(MathHelper.floor(player.getPosX() + (i%3) - 1), MathHelper.floor(player.getPosY() - 2.1D - player.getYOffset()), MathHelper.floor(player.getPosZ()) + (i/3 - 1)).up();
                    Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                    if (blockAtLocation == Blocks.AIR) {
                        player.world.setBlockState(blockLocation, conjurerBlock.getDefaultState(), 3);
                    }
                }

            }
        }
    }

    private static void placeLightSourceBlock(Entity player, BlockPos blockLocation, NenLight theLightBlock) {
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