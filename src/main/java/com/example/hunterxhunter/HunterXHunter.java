package com.example.hunterxhunter;

import com.mojang.logging.LogUtils;
import com.packets.PacketManager;
import com.screens.BookInventoryScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.registry.Biomes.BIOMES;
import static com.registry.Blocks.BLOCKS;
import static com.registry.Items.ITEMS;
import static com.registry.MenuTypes.BOOK_MENU;
import static com.registry.MenuTypes.MENU_TYPES;
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

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        BIOMES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        MENU_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
        HunterXHunter.LOGGER.info("Client setup");
        // Not thread safe
        event.enqueueWork(
                () -> MenuScreens.register(BOOK_MENU.get(), BookInventoryScreen::new)
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Common setup");
        PacketManager.register();
    }
}
