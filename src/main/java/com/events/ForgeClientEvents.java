package com.events;

import com.screens.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
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
            yo.setOpenedBook(true);
            yo.setLastOpenedBook(Util.milliTime());
            LOGGER.info("pressed book button");
            Minecraft.getInstance().player.playSound(OPEN_BOOK.get(), 1, 1);
            BookItemStackHandler oof = (BookItemStackHandler) event.player.getCapability(BOOK_CAPABILITY).orElseThrow(null);
            PacketManager.sendToServer(new SyncBookPacket(event.player.getEntityId(), (CompoundNBT) BOOK_CAPABILITY.writeNBT(oof, null)));
        }
    }
}
