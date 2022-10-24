package com.events;

import com.screens.Debug;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.hunterxhunter.HunterXHunter.MODID;
import static com.registry.KeyMappings.devTesting;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ForgeServerEvents {
    @SubscribeEvent
    public static void onDevTesting(InputEvent.Key event) {
        if(event.getKey() == devTesting.getKey().getValue()) {
            Debug debug = new Debug();
            Minecraft.getInstance().setScreen(debug);
        }
    }
}
