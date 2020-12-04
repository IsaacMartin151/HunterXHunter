package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.client.core.handler.ClientProxy;
import com.chubbychump.hunterxhunter.client.gui.ContainerScreenGreedIsland;
import com.chubbychump.hunterxhunter.client.rendering.RayBeamRenderer;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.types.Enhancer;
import com.chubbychump.hunterxhunter.common.core.IProxy;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import com.chubbychump.hunterxhunter.util.VillagerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.chubbychump.hunterxhunter.client.core.handler.ClientProxy.*;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.GREED_ISLAND_CONTAINER;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.MASADORIAN_POI;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("hunterxhunter")
public class HunterXHunter {
    private static File file = new File(Minecraft.getInstance().getFileResourcePacks().getAbsolutePath()+"/departure.avi");
    public static FFmpegFrameGrabber eff = new FFmpegFrameGrabber(file);
    private static File file2 = new File(Minecraft.getInstance().getFileResourcePacks().getAbsolutePath()+"/hisoka.avi");
    public static FFmpegFrameGrabber fff = new FFmpegFrameGrabber(file2);
    public static IProxy proxy = new IProxy() {};
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hunterxhunter";

    private static final UUID MODIFIER_ID = UUID.fromString("81f27f52-c8bb-403a-a1a4-b356d2f7a0f0");
    //FIX FILE PATH FFMPEG FILE DEPARTURE.MP4
    //Add effect bloodlust? Must kill an entity
    //Keep a running list of blockitems vs Items vs foods vs tools in the 100
    // - 5 food
    // - 20 usable items
    // - 30 crafting ingredients
    // - 10 placeable
    // - 15 tools

    // - 30 vanilla items

    //Transmuter = fully repair durability - increased speed?
    //Enhancer = increased health, damage, regen?
    //Emitter = create explosions at range, explosion based on power
    //Conjurer = create winged ally entities?
    //Manipulator = view other people's perspectives, freeze people/control movement few seconds after hitting them with projectile - custom enchantment for that?
    //Specialist = Really high damage + health cap?

    /*
    Base it off the game, win the game by collecting all the cards. Have the Cardbook storage be a capability?
    - Custom dimension for after all vanilla items are collected ~70 or so?
    - "Cards" need to be able to be converted to and from item form, so each card needs corresponding item (Not all need to be new items)
    - If card of certain rarity, play sound - store in giant public static int array?
    -
    - Custom cutscene for when someone beats the game, gets something cool and permanent card
     */

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
        VillagerUtil.fixPOITypeBlockStates(MASADORIAN_POI.get());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //RenderingRegistry.registerEntityRenderingHandler(RAYBEAM, RayBeamRenderer::new);
        CapabilityManager.INSTANCE.register(NenUser.class, new NenStorage(), Enhancer::new);
        CapabilityManager.INSTANCE.register(IMoreHealth.class, new MoreHealthStorage(), MoreHealth::new);
        MinecraftForge.EVENT_BUS.register(new IngameGui(Minecraft.getInstance()));
        ScreenManager.registerFactory(GREED_ISLAND_CONTAINER.get(), ContainerScreenGreedIsland::new);
        ClientRegistry.registerKeyBinding(nenControl);
        ClientRegistry.registerKeyBinding(increaseNen);
        ClientRegistry.registerKeyBinding(gyo);
        ClientRegistry.registerKeyBinding(nenPower1);
        ClientRegistry.registerKeyBinding(nenPower2);
        //event.enqueueWork(eff)
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

        // Health Loop
        // Add a heart to the player if...
        // - Ramp position is less than the ramp length AND
        // - The player's level is greater than the next ramp position AND
        // - The player has not hit the maximum health
        LazyOptional<NenUser> yo = player.getCapability(NenProvider.MANA_CAP, null);
        int Type = yo.orElseThrow(null).getNenType();
        while (cap.getRampPosition() < ramp.size() && level >= ramp.get(cap.getRampPosition()) && (player.getMaxHealth() < max || Type == 1)) {
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

        // Post-loop changes
        // If changed...
        // - Notify the client of the changes to the capability
        // - Set the player to max health
        if (changed) {
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            player.setHealth(player.getMaxHealth());
        }
    }
}
