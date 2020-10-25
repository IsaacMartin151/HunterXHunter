/*package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthStorage;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

@Mod(LevelHearts.MOD_ID)
@EventBusSubscriber(modid = LevelHearts.MOD_ID, bus = Bus.MOD)
public class LevelHearts {

    public static final String MOD_ID = "levelhearts";
    public static final String MOD_NAME = "LevelHearts";
    public static final String MOD_VERSION = "2.2.0";
    private static final UUID MODIFIER_ID = UUID.fromString("81f27f52-c8bb-403a-a1a4-b356d2f7a0f0");
    private static final Logger logger = LogManager.getLogger(LevelHearts.MOD_ID);

    public LevelHearts() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "levelhearts.toml");
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IMoreHealth.class, new MoreHealthStorage(), MoreHealth::new);
        PacketManager.register();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new IngameGui());
    }

    public static void debug(String message) {
        if (Config.enableDebug.get()) {
            logger.info(message);
        }
    }

    public static void debug(Object object) {
        debug(object.toString());
    }

    public static void error(Exception exception) {
        logger.throwing(exception);
    }

    public static void applyHealthModifier(PlayerEntity player, double amount) {
        debug("LevelHearts#applyHealthModifier");
        AttributeModifier modifier = new AttributeModifier(MODIFIER_ID, "levelHearts.healthModifier", amount, Operation.ADDITION);
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.field_233818_a_);
        attribute.removeModifier(modifier);
        //attribute.func_225504_a_(modifier); // going for applyPersistentModifier(modifier);
    }

    public static void recalcPlayerHealth(PlayerEntity player, int level) {
        debug("LevelHearts#calcPlayerHealth");

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
        while (cap.getRampPosition() < ramp.size() && level >= ramp.get(cap.getRampPosition()) && player.getMaxHealth() < max) {
            debug("LevelHearts#calcPlayerHealth: Adding a heart to the player.");
            changed = true;

            // Increase ramp position, add two half hearts, and notify the player
            cap.addRampPosition();
            cap.setModifier(cap.getModifier() + 2); // Add two-half hearts

            // Apply the health modifier so player.getMaxHealth() returns the correct ammount
            applyHealthModifier(player, cap.getTrueModifier());
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

} */
