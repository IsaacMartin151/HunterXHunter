package com.chubbychump.hunterxhunter.client.core.handler;

import com.chubbychump.hunterxhunter.init.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.TickEvent;

public final class ClientTickHandler {

    private ClientTickHandler() {}

    public static int pageFlipTicks = 0;
    public static int ticksInGame = 0;
    public static float partialTicks = 0;
    public static float delta = 0;
    public static float total = 0;

    private static void calcDelta() {
        float oldTotal = total;
        total = ticksInGame + partialTicks;
        delta = total - oldTotal;
    }

    public static void renderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START) {
            partialTicks = event.renderTickTime;
            calcDelta();
        }
    }

    /*public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ItemsRemainingRenderHandler.tick();

            if (!Minecraft.getInstance().isGamePaused()) {
                ticksInGame++;
                partialTicks = 0;

                PlayerEntity player = Minecraft.getInstance().player;
                if (player != null) {
                    if (PlayerHelper.hasHeldItemClass(player, ModItems.twigWand)) {
                        for (TileEntity tile : ImmutableList.copyOf(ManaNetworkHandler.instance.getAllCollectorsInWorld(Minecraft.getInstance().world))) {
                            if (tile instanceof IManaCollector) {
                                ((IManaCollector) tile).onClientDisplayTick();
                            }
                        }
                    }
                }
            }
            calcDelta();
        }
    } */

    public static void notifyPageChange() {
        if (pageFlipTicks == 0) {
            pageFlipTicks = 5;
        }
    }
}