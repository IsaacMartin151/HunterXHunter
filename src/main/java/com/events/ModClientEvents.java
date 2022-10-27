package com.events;

import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.example.hunterxhunter.HunterXHunter.MODID;
import static com.registry.KeyMappings.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        HunterXHunter.LOGGER.info("--- Client Setup ---");
        HunterXHunter.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void keyMappings(RegisterKeyMappingsEvent event) {
        // Dev tools
        event.register(devTesting);
        event.register(increaseNen);

        // Actual controls
        event.register(nenToggle);
        event.register(gyoToggle);
        event.register(nenPower1);
        event.register(nenPower2);
        event.register(book);
        event.register(transformCard);
    }
}
