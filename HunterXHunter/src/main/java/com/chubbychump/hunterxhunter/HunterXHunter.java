package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealthStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenStorage;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.types.Enhancer;
import com.chubbychump.hunterxhunter.client.rendering.RayBeamRenderer;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.chubbychump.hunterxhunter.init.ModEntityTypes;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

import static com.chubbychump.hunterxhunter.init.ModEntityTypes.RAYBEAM;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.RAY_BEAM_ENTITY;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("hunterxhunter")
public class HunterXHunter {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hunterxhunter";
    private static final UUID MODIFIER_ID = UUID.fromString("81f27f52-c8bb-403a-a1a4-b356d2f7a0f0");
    public static KeyBinding nenControl = new KeyBinding("Toggle Nen", 67, "Nen Abilities");
    public static KeyBinding increaseNen = new KeyBinding("Increase Nen", 86, "Nen Abilities");
    public static KeyBinding gyo = new KeyBinding("Toggle Gyo", 89, "Nen Abilities");
    public static KeyBinding nenPower1 = new KeyBinding("Nen Power 1", 88, "Nen Abilities");
    public static KeyBinding nenPower2 = new KeyBinding("Nen Power 2", 90, "Nen Abilities");

    public HunterXHunter() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        RegistryHandler.init();
        MinecraftForge.EVENT_BUS.register(this);

        ClientRegistry.registerKeyBinding(nenControl);
        ClientRegistry.registerKeyBinding(increaseNen);
        ClientRegistry.registerKeyBinding(gyo);
        ClientRegistry.registerKeyBinding(nenPower1);
        ClientRegistry.registerKeyBinding(nenPower2);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ShaderHelper.initShaders();
        RenderingRegistry.registerEntityRenderingHandler(RAYBEAM, RayBeamRenderer::new);
        CapabilityManager.INSTANCE.register(NenUser.class, new NenStorage(), Enhancer::new);
        CapabilityManager.INSTANCE.register(IMoreHealth.class, new MoreHealthStorage(), MoreHealth::new);
        MinecraftForge.EVENT_BUS.register(new IngameGui(Minecraft.getInstance()));
        PacketManager.register();
    }


    public static final ItemGroup TAB = new ItemGroup("hunterxhunter") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.RUBY.get());
        }
    };

    public static void applyHealthModifier(PlayerEntity player, double amount) {
        AttributeModifier modifier = new AttributeModifier(MODIFIER_ID, "levelHearts.healthModifier", amount, AttributeModifier.Operation.ADDITION);
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.field_233818_a_);
        attribute.removeModifier(modifier);
        attribute.func_233767_b_(modifier); //Maybe 769?
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
        while (cap.getRampPosition() < ramp.size() && level >= ramp.get(cap.getRampPosition()) && player.getMaxHealth() < max) {
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
}
