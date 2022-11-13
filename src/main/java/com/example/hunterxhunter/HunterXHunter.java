package com.example.hunterxhunter;

import com.mojang.logging.LogUtils;
import com.packets.PacketManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.registry.Biomes.BIOMES;
import static com.registry.Blocks.BLOCKS;
import static com.registry.Items.ITEMS;
import static com.registry.MobEffects.MOB_EFFECTS;
import static com.registry.Sounds.SOUNDS;

@Mod(HunterXHunter.MODID)
public class HunterXHunter
{
    //Enhancer - give speed, jump boost, strength, (chance of?) bloodlust
    public static final String MODID = "hunterxhunter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HunterXHunter()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        BIOMES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Common setup");
        PacketManager.register();
    }
}
