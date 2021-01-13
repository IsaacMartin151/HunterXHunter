package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.client.core.handler.ClientProxy;
import com.chubbychump.hunterxhunter.client.gui.ContainerScreenGreedIsland;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.common.abilities.greedislandbook.BookStorage;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.core.IProxy;
import com.chubbychump.hunterxhunter.common.entities.renderers.NeferpitouRenderer;
import com.chubbychump.hunterxhunter.common.entities.renderers.ShiapoufCloneRenderer;
import com.chubbychump.hunterxhunter.common.entities.renderers.ShiapoufRenderer;
import com.chubbychump.hunterxhunter.common.entities.renderers.YoupiRenderer;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import com.chubbychump.hunterxhunter.util.VillagerUtil;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

@Mod("hunterxhunter")
public class HunterXHunter {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hunterxhunter";
    public static IProxy proxy = new IProxy() {};
    private static final UUID MODIFIER_ID = UUID.fromString("81f27f52-c8bb-403a-a1a4-b356d2f7a0f0");

    //Phantom Troupe final boss battle for last item - use theme music

    //Add Neferpitou animation overlay for BloodLust

    //Add custom screen for every type? Kinda an update screen for current nen capacity + abilities

    //Create animation for adding card to book
    // Separate categories for the types of hunters
    // Beast Hunters, Blacklist Hunters - killing strong mobs, Botanical Hunters, Sea Hunters, Treasure Hunters, Ruins Hunters
    // - 5 food
    // - 20 usable items
    // - 30 crafting ingredients
        // 5 regular mob dropx
        // 2 minerals
        // 3 world gen - new tree (World tree?), other overworld structure/flowers
        // 1 boss drop?
        //

    // Waila shows if block is harvestable
    // - 10 placeable
    // - 15 tools

    //KilledTrigger

    // - Biome structure loot
    // - Animals in Tundra, Badlands, Swamp, Jungle, Plains, maybe new biome?

    // New biome with tough monster, add "Bomber" underground entity

    // Loot chests from dungeons, abandoned mineshafts, strongholds can hold treasure items

    // - 30 vanilla items

    //Transmuter = Enchantment, AOE electrocute
    // - Button 1: Dash Move
    //Enhancer = increased health, damage, regen?
    // - Button 1: Select
    // - Button 1: Aerial Attack
    //Emitter = create explosions at range, explosion based on power
    // - Button 1: Select Projectile Type
    // - Button 2:
    //Conjurer = create winged ally entities? Create temporary walls/floors/steps/protective pyramid
    // - Button 1: Select Structure Type/Create Structure
    // - Button 2:
    //Manipulator = view other people's perspectives, freeze/control movement few seconds after hitting them with projectile
    // - Button 1: Select person/Toggle overlay
    // - Button 2: Select Post-Damage effect
    //Specialist = Really high damage + health cap?

    /*
    - Custom dimension for after all items are collected
    // Boss battle - 4 islands, 1 starting island and 3 opponent islands
    // ShiaPouf creates phantom entities, 2 per player, in order to satisfy bloodlust
    // Youpi detonates random locations
    // Neferpitou causes bloodlust, ranged attack

    // Add a blindness thing to get Gyo to actually do something - maybe ren?

    - "Cards" need to be able to be converted to and from item form, so each card needs corresponding item (Not all need to be new items)
    - If card of certain rarity, play sound
    - Cool Animation for adding card to the book feat. transforming into card texture vs regular texture
    -
    - Custom cutscene for when someone beats the game, gets something cool and permanent card
     */

    //Rod that launches you in a direction - tool

    //Custom structure at start to get "Hunter License" - use NBT data from woodland mansion, build custom setup, then scan it
    // - 1. Beating Satotz in a race - Race while rendering an entity
    // - 2. Foggy Maze
    // - 3. Tower of traps
    // - 4. Press button to get one book

    //TemplateManager templateManager = player.getEntityWorld().getServer().getTemplateManager();
    //templateManager.getTemplate();


    public HunterXHunter() {
        DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> proxy = new ClientProxy());
        proxy.registerHandlers();        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        RegistryHandler.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketManager.register(); //32.0.63
        CapabilityManager.INSTANCE.register(INenUser.class, new NenStorage(), () -> new NenUser());
        CapabilityManager.INSTANCE.register(IMoreHealth.class, new MoreHealthStorage(), () -> new MoreHealth());
        CapabilityManager.INSTANCE.register(ItemStackHandler.class, new BookStorage(), () -> new BookItemStackHandler(100));
        VillagerUtil.fixPOITypeBlockStates(MASADORIAN_POI.get());
        GlobalEntityTypeAttributes.put(SHIAPOUF_CLONE_ENTITY.get(), MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D)
                .create());
        GlobalEntityTypeAttributes.put(YOUPI_ENTITY.get(), MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.8F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 100.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(NEFERPITOU_ENTITY.get(), MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.6F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 40.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(SHIAPOUF_ENTITY.get(), MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)1.0F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SHIAPOUF_ENTITY.get(), ShiapoufRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(YOUPI_ENTITY.get(), YoupiRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NEFERPITOU_ENTITY.get(), NeferpitouRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SHIAPOUF_CLONE_ENTITY.get(), ShiapoufCloneRenderer::new);

        MinecraftForge.EVENT_BUS.register(new IngameGui(Minecraft.getInstance()));
        ScreenManager.registerFactory(GREED_ISLAND_CONTAINER.get(), ContainerScreenGreedIsland::new);
    }

    public static final ItemGroup TAB = new ItemGroup("hunterxhunter") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.RUBY.get());
        }
    };

    public static void applyHealthModifier(PlayerEntity player, double amount) {
        AttributeModifier modifier = new AttributeModifier(MODIFIER_ID, "levelHearts.healthModifier", amount, AttributeModifier.Operation.ADDITION);
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        attribute.removeModifier(modifier);
        attribute.applyPersistentModifier(modifier); //Maybe 769?
    }

    public static void recalcPlayerHealth(PlayerEntity player, int level) {
        // Capability, Config Files, & Local Variables
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        ArrayList<Integer> ramp = Config.levelRamp.get();
        int max = Config.maxHealth.get() > 0 ? Config.maxHealth.get() : 1024;
        boolean changed = false;

        // Validate information
        if (cap.getRampPosition() < 0) {
            cap.setRampPosition((short) 0);
        }

        LazyOptional<INenUser> yo = player.getCapability(NENUSER, null);
        int Type = yo.orElseThrow(null).getNenType();
        while (cap.getRampPosition() < ramp.size() && (player.getMaxHealth() < max || Type == 1)) {
            changed = true;

            // Increase ramp position, add two half hearts, and notify the player
            cap.addRampPosition();
            cap.setModifier(cap.getModifier() + 2); // Add two-half hearts

            // Apply the health modifier so player.getMaxHealth() returns the correct amount
            if (Type == 1) {
                applyHealthModifier(player, cap.getEnhancerModifier());
            }
            else {
                applyHealthModifier(player, cap.getTrueModifier());
            }
        }

        if (changed) {
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            player.setHealth(player.getMaxHealth());
        }
    }
}
