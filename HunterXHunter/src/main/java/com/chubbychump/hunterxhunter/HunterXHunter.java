package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.common.abilities.NenStorage;
import com.chubbychump.hunterxhunter.common.abilities.NenUser;
import com.chubbychump.hunterxhunter.common.abilities.types.Enhancer;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("hunterxhunter")
public class HunterXHunter
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hunterxhunter";


    public HunterXHunter() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        RegistryHandler.init();
        MinecraftForge.EVENT_BUS.register(this);

        KeyBinding nenControl = new KeyBinding("Toggle Nen", 47, "Nen Abilities");
        ClientRegistry.registerKeyBinding(nenControl);
    }
    private void setup(final FMLCommonSetupEvent event) { }
    private void doClientStuff(final FMLClientSetupEvent event) {
        CapabilityManager.INSTANCE.register(NenUser.class, new NenStorage(), Enhancer::new);
    }

    public static final ItemGroup TAB = new ItemGroup("hunterxhunter") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.RUBY.get());
        }
    };
}
