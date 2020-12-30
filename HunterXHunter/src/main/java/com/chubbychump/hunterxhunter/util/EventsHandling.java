package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.*;
import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.containers.NenEffectSelect;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.packets.SyncBookPacket;
import com.chubbychump.hunterxhunter.packets.SyncNenPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
//import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;

import static com.chubbychump.hunterxhunter.HunterXHunter.*;
import static com.chubbychump.hunterxhunter.client.core.handler.ClientProxy.*;
import static com.chubbychump.hunterxhunter.client.gui.HUDHandler.drawSimpleManaHUD;
import static com.chubbychump.hunterxhunter.common.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.init.ModBlocks.NENLIGHT;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

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
        NenUser yo = NenUser.getFromPlayer(player);
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
        NenUser nenOld = NenUser.getFromPlayer(playerOld);
        NenUser nenNew = NenUser.getFromPlayer(playerNew);

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
        NenUser nenCap = NenUser.getFromPlayer(player);

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
        NenUser yo = NenUser.getFromPlayer(player);
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
        NenUser yo = NenUser.getFromPlayer(player);
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
    public static void renderEvent(RenderLivingEvent.Post event) { //RenderWorldLastEvent for drawing stuff

        Minecraft bruh = Minecraft.getInstance();
        if (!bruh.isGamePaused()) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.isAlive()) {
                NenUser yo = NenUser.getFromPlayer(player);
                boolean erm = yo.getGyo();
                int eee = yo.getNenType();
                if (erm && yo.getCurrentNen() > 0) {
                    showMobs(event.getMatrixStack(), event.getBuffers(), event.getEntity());
                }
            }
        }
    }

    /*
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void nenSelect(RenderGameOverlayEvent.Post) {
        if (!modeMenu.isInvalid() && modeMenu.isKeyDown())
        {
            ChiselsAndBitsMenu.instance.actionUsed = false;
            ChiselsAndBitsMenu.instance.raiseVisibility();
            ChiselsAndBitsMenu.instance.getMinecraft().mouseHelper.ungrabMouse();
        }
        else
        {
            if (!ChiselsAndBitsMenu.instance.actionUsed)
            {
                if (ChiselsAndBitsMenu.instance.switchTo != null)
                {
                    ClientSide.instance.playRadialMenu();
                    ChiselModeManager.changeChiselMode(tool, ChiselModeManager.getChiselMode(getPlayer(), tool, Hand.MAIN_HAND), ChiselsAndBitsMenu.instance.switchTo);
                }

                if (ChiselsAndBitsMenu.instance.doAction != null)
                {
                    ClientSide.instance.playRadialMenu();
                    switch (ChiselsAndBitsMenu.instance.doAction)
                    {
                        case ROLL_X:
                            PacketRotateVoxelBlob pri = new PacketRotateVoxelBlob(Axis.X, Rotation.CLOCKWISE_90);
                            ChiselsAndBits.getNetworkChannel().sendToServer(pri);
                            break;

                        case ROLL_Z:
                            PacketRotateVoxelBlob pri2 = new PacketRotateVoxelBlob(Axis.Z, Rotation.CLOCKWISE_90);
                            ChiselsAndBits.getNetworkChannel().sendToServer(pri2);
                            break;

                        case REPLACE_TOGGLE:
                            ReplacementStateHandler.getInstance().setReplacing(!ReplacementStateHandler.getInstance().isReplacing());
                            ReflectionWrapper.instance.clearHighlightedStack();
                            break;

                        case UNDO:
                            UndoTracker.getInstance().undo();
                            break;

                        case REDO:
                            UndoTracker.getInstance().redo();
                            break;

                        case BLACK:
                        case BLUE:
                        case BROWN:
                        case CYAN:
                        case GRAY:
                        case GREEN:
                        case LIGHT_BLUE:
                        case LIME:
                        case MAGENTA:
                        case ORANGE:
                        case PINK:
                        case PURPLE:
                        case RED:
                        case LIGHT_GRAY:
                        case WHITE:
                        case YELLOW:

                            final PacketSetColor setColor = new PacketSetColor(DyeColor.valueOf(ChiselsAndBitsMenu.instance.doAction.name()),
                                    getHeldToolType(Hand.MAIN_HAND),
                                    ChiselsAndBits.getConfig().getClient().chatModeNotification.get());
                            ChiselsAndBits.getNetworkChannel().sendToServer(setColor);
                            ReflectionWrapper.instance.clearHighlightedStack();

                            break;
                    }
                }
            }

            ChiselsAndBitsMenu.instance.actionUsed = true;
            ChiselsAndBitsMenu.instance.decreaseVisibility();
        }

        if (ChiselsAndBitsMenu.instance.isVisible())
        {
            final MainWindow window = event.getWindow();
            ChiselsAndBitsMenu.instance.init(Minecraft.getInstance(), window.getScaledWidth(), window.getScaledHeight());
            ChiselsAndBitsMenu.instance.configure(window.getScaledWidth(), window.getScaledHeight());

            if (!wasVisible)
            {
                ChiselsAndBitsMenu.instance.getMinecraft().currentScreen = ChiselsAndBitsMenu.instance;
                ChiselsAndBitsMenu.instance.getMinecraft().mouseHelper.ungrabMouse();
            }

            if (ChiselsAndBitsMenu.instance.getMinecraft().mouseHelper.isMouseGrabbed())
            {
                KeyBinding.unPressAllKeys();
            }

                //final int k1 = (int) (Minecraft.getInstance().mouseHelper.getMouseX() * window.getScaledWidth() / window.getWidth());
                //final int l1 = (int) (window.getScaledHeight() - Minecraft.getInstance().mouseHelper.getMouseY() * window.getScaledHeight() / window.getHeight() - 1);
                //net.minecraftforge.client.ForgeHooksClient.drawScreen(ChiselsAndBitsMenu.instance, event.getMatrixStack(), k1, l1, event.getPartialTicks());
        }
        else
        {
            if (wasVisible)
            {
                ChiselsAndBitsMenu.instance.getMinecraft().mouseHelper.grabMouse();
            }
        }
    }

        if (!undo.isInvalid() && undo.isPressed())
    {
        UndoTracker.getInstance().undo();
    }

        if (!redo.isInvalid() && redo.isPressed())
    {
        UndoTracker.getInstance().redo();
    }

        if (!addToClipboard.isInvalid() && addToClipboard.isPressed())
    {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK && mc.objectMouseOver instanceof BlockRayTraceResult)
        {
            final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) mc.objectMouseOver;

            try
            {
                final IBitAccess access = ChiselsAndBits.getApi().getBitAccess(mc.world, rayTraceResult.getPos());
                final ItemStack is = access.getBitsAsItem(null, ItemType.CHISLED_BLOCK, false);

                CreativeClipboardTab.addItem(is);
            }
            catch (final CannotBeChiseled e)
            {
                // nope.
            }
        }
    }

        if (!pickBit.isInvalid() && pickBit.isPressed())
    {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK && mc.objectMouseOver instanceof BlockRayTraceResult)
        {
            BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) mc.objectMouseOver;

            try
            {
                final BitLocation bl = new BitLocation(rayTraceResult, true, BitOperation.CHISEL);
                final IBitAccess access = ChiselsAndBits.getApi().getBitAccess(mc.world, bl.getBlockPos());
                final IBitBrush brush = access.getBitAt(bl.getBitX(), bl.getBitY(), bl.getBitZ());
                final ItemStack is = brush.getItemStack(1);
                doPick(is);
            }
            catch (final CannotBeChiseled e)
            {
                // nope.
            }
        }
    }

        if (type == ElementType.HOTBAR && ChiselsAndBits.getConfig().getClient().enableToolbarIcons.get())
    {
        final Minecraft mc = Minecraft.getInstance();
        final MainWindow window = event.getWindow();

        if (!mc.player.isSpectator())
        {
            final IngameGui sc = mc.ingameGUI;

            for (int slot = 0; slot < 9; ++slot)
            {
                final ItemStack stack = mc.player.inventory.mainInventory.get(slot);
                if (stack.getItem() instanceof ItemChisel)
                {
                    final ChiselToolType toolType = getToolTypeForItem(stack);
                    IToolMode mode = toolType.getMode(stack);

                    if (!ChiselsAndBits.getConfig().getClient().perChiselMode.get() && tool == ChiselToolType.CHISEL)
                    {
                        mode = ChiselModeManager.getChiselMode(mc.player, ChiselToolType.CHISEL, lastHand);
                    }

                    final int x = window.getScaledWidth() / 2 - 90 + slot * 20 + 2;
                    final int y = window.getScaledHeight() - 16 - 3;

                    RenderSystem.color4f(1, 1, 1, 1.0f);
                    Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
                    final TextureAtlasSprite sprite = chiselModeIcons.get(mode) == null ? getMissingIcon() : chiselModeIcons.get(mode).sprite;

                    RenderSystem.enableBlend();
                    sc.blit(event.getMatrixStack(), x + 1, y + 1, 0, 8, 8, sprite);
                    RenderSystem.disableBlend();
                }
            }
        }
    }
}
    }
    */

    //CAN NOT make this client only
    // Well, maybe I can if I check for client side only instead of server side only
    // Nah, doesn't look like that works. Client side capability changes don't seem to update the server side capability
    // Sending packet to server to update capability, gonna see if it works
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void keyPress(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && event.phase == TickEvent.Phase.START && event.player.getEntityWorld().isRemote()) {
            NenUser yo = NenUser.getFromPlayer(event.player);
            boolean updatePlayer = false;
            if (book.isPressed()) {
                updatePlayer = true;
                //INamedContainerProvider uh = new GreedIslandContainer();
                LOGGER.info("pressed book button");
                BookItemStackHandler oof = (BookItemStackHandler) event.player.getCapability(BOOK_CAPABILITY).orElseThrow(null);
                PacketManager.sendToServer(new SyncBookPacket(event.player.getEntityId(), (CompoundNBT) BOOK_CAPABILITY.writeNBT(oof, null)));

                //NenUser cap = NenUser.getFromPlayer(serverPlayer);
            }
            if (nenControl.isPressed()) {
                yo.toggleNen();
                updatePlayer = true;
            }
            if (increaseNen.isPressed()) {
                yo.increaseNenPower(event.player);
                updatePlayer = true;
            }
            if (gyo.isPressed()) {
                yo.toggleGyo();
                updatePlayer = true;
            }
            if (nenPower1.isPressed()) {
                Minecraft.getInstance().displayGuiScreen(NenEffectSelect.instance);
                /*
                switch (yo.getNenType()) {
                    case 0: break;
                    case 1:
                        event.player.setForcedPose(Pose.FALL_FLYING);
                        event.player.setNoGravity(true);
                    case 2:
                        Pose uh;
                        yo.nenpower1(event.player);
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        updatePlayer = true;
                        World world = event.player.world;
                        PlayerEntity player = event.player;
                        BlockPos pos = event.player.getPosition();
                        //if (!world.isRemote && player != null COST, false) {
                        LOGGER.info("Laser beam! BlockPos is " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
                        //Entity entity = ModEntityTypes.RAYBEAM.create(world);
                        //entity.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        //world.addEntity(entity);
                        //player.getCooldownTracker().setCooldown(this, IManaProficiencyArmor.hasProficiency(player, stack) ? COOLDOWN / 2 : COOLDOWN);
                        //ManaItemHandler.instance().requestManaExactForTool(stack, player, COST, true);
                        //}
                        //If manipulator, set screen to extended map gui
                        //MapItemRenderer


                }
                 */
            }
            if (updatePlayer == true) {
                NenUser.updateServer(event.player, yo);
            }
        }
    }

    @SubscribeEvent
    public static void potions(PotionEvent.PotionExpiryEvent event) {
        LOGGER.info("Potion expiring");
        if (event.getPotionEffect().getPotion() == BLOODLUST_EFFECT.get() ) {
            LOGGER.info("Expiring potion is bloodlust, killing player");
            event.getEntity().attackEntityFrom(DamageSource.STARVE, 100);
        }
    }

    @SubscribeEvent
    public static void potions(PotionEvent.PotionRemoveEvent event) {
        LOGGER.info("Trying to remove potion");
        if (event.getPotionEffect().getPotion() == BLOODLUST_EFFECT.get()) {
            LOGGER.info("It's an instance of bloodlust, cancelling removal");
        }
    }

    //@OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public static void calculateNenEffects(TickEvent.PlayerTickEvent event) {
        if (event.player.isAlive() && !event.player.getEntityWorld().isRemote && event.phase == TickEvent.Phase.START) {
            NenUser yo = NenUser.getFromPlayer(event.player);
            int cost = 0;
            if (yo.isNenActivated()) {
                cost += 1;
            }
            if (yo.getGyo()) {
                cost += 1;
            }
            if (yo.getEn()) {
                cost += 1;
            }
            yo.setCurrentNen(yo.getCurrentNen() - cost);
            if (yo.getCurrentNen() - cost < 0) {
                yo.setCurrentNen(0);
                yo.resetNen();
            }
            if (cost == 0) {
                yo.setCurrentNen(yo.getCurrentNen() + 2);
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
            /*if (Botania.proxy.isClientPlayerWearingMonocle()) {
                profiler.startSection("monocle");
                ItemMonocle.renderHUD(ms, mc.player);
                profiler.endSection();
            }*/
            profiler.startSection("manaBar");
            PlayerEntity player = mc.player;
            if (!player.isSpectator() && player.isAlive()) {
                NenUser yo = NenUser.getFromPlayer(player);
                boolean anyRequest = (yo.getNenPower() > 0);
                int totalMana = yo.getCurrentNen();
                int totalMaxMana = yo.getMaxCurrentNen();
                //Successfully synced capabilities up, this is the render thread
                if (anyRequest) {
                    drawSimpleManaHUD(ms, 0x4444FF, totalMana, totalMaxMana, "Nen");
                }
            }
            profiler.endStartSection("itemsRemaining");
            //ItemsRemainingRenderHandler.render(ms, event.getPartialTicks());
            profiler.endSection();
            profiler.endSection();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
        }
    }

    private static void processLightPlacementForEntities(World theWorld) {
        for (Entity player : Collections.unmodifiableList(theWorld.getPlayers())) {
            NenUser yo = NenUser.getFromPlayer((PlayerEntity) player);
            if (yo.isNenActivated() && yo.getCurrentNen() > 0) {
                BlockPos blockLocation = new BlockPos(MathHelper.floor(player.getPosX()), MathHelper.floor(player.getPosY() - 0.1D - player.getYOffset()), MathHelper.floor(player.getPosZ())).up();
                Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                NenLight lightBlockToPlace = NENLIGHT;
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(player, blockLocation, lightBlockToPlace);
                }
            }
        }
    }

    private static void placeLightSourceBlock(Entity player, BlockPos blockLocation, NenLight theLightBlock) {
        player.world.setBlockState(blockLocation, theLightBlock.getDefaultState(), 3);
        TileEntityNenLight lightSource = (TileEntityNenLight) player.world.getTileEntity(blockLocation);
        NenUser yo = NenUser.getFromPlayer((PlayerEntity) player);
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
            //LazyOptional<NenUser> yo = entity.getCapability(NenProvider.NENUSER, null);
            //int[] bruh2 = yo.orElseThrow(null).getNencolor();
            //float oof = (float) (yo.orElseThrow(null).getNenPower()/2.);
            int[] yuh = {500, 200, 0};
            ObjectDrawingFunctions.DrawDragon(matrixStack, yuh);
            //ObjectDrawingFunctions.DrawSphere(matrixStack, bruh2, oof);
        } else {
            int[] uh = {1000, 500, 0};
            ObjectDrawingFunctions.DrawSphere(matrixStack, uh, 2);
        }
    }

}