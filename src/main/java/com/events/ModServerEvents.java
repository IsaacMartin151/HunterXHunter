package com.events;

import com.example.hunterxhunter.HunterXHunter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.hunterxhunter.HunterXHunter.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ModServerEvents {
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        HunterXHunter.LOGGER.info("--- Server starting ---");
    }

}
