package com.events;

import com.screens.Debug;
import com.screens.MainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.hunterxhunter.HunterXHunter.MODID;
import static com.registry.KeyMappings.book;
import static com.registry.KeyMappings.devTesting;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onDevTesting(InputEvent.Key event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(event.getKey() == devTesting.getKey().getValue()) {
            Debug debug = new Debug();
            Minecraft.getInstance().setScreen(debug);
        }
        else if (event.getKey() == book.getKey().getValue()) {

        }
    }

    @SubscribeEvent
    public static void onMainMenu(ScreenEvent.Init event) {
        if (event.getScreen() instanceof TitleScreen) {
            event.getScreen().getMinecraft().setScreen(new MainMenu());
        }
    }
}
