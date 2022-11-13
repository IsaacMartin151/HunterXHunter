package com.events;

import com.abilities.greedislandbook.BookItemStackHandler;
import com.abilities.greedislandbook.GreedIslandProvider;
import com.abilities.heartstuff.IMoreHealth;
import com.abilities.heartstuff.MoreHealth;
import com.abilities.heartstuff.MoreHealthProvider;
import com.abilities.nenstuff.INenUser;
import com.abilities.nenstuff.NenProvider;
import com.abilities.nenstuff.NenUser;
import com.example.hunterxhunter.HunterXHunter;
import com.mojang.logging.LogUtils;
import com.packets.PacketManager;
import com.packets.SyncBookPacket;
import com.screens.BookInventoryScreen;
import com.screens.Debug;
import com.screens.MainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.items.ItemStackHandler;

import static com.abilities.greedislandbook.GreedIslandProvider.BOOK_CAPABILITY;
import static com.example.hunterxhunter.HunterXHunter.MODID;
import static com.registry.KeyMappings.*;
import static com.registry.MenuTypes.BOOK_MENU;

@Mod.EventBusSubscriber
public class ModEvents
{
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LogUtils.getLogger().info("--- Server starting ---");
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        HunterXHunter.LOGGER.info("Registering Capabilities");
        event.register(INenUser.class);
        event.register(IMoreHealth.class);
        event.register(BookItemStackHandler.class);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        // Not thread safe
        event.enqueueWork(
                () -> MenuScreens.register(BOOK_MENU.get(), BookInventoryScreen::new)
        );

        //MinecraftForge.EVENT_BUS.register(new IngameGui());
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

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            HunterXHunter.LOGGER.info("Attaching Player Capabilities");
            event.addCapability(new ResourceLocation(MODID, "nenuser"), new NenProvider());
            event.addCapability(new ResourceLocation(MODID, "morehealth"), new MoreHealthProvider());
            event.addCapability(new ResourceLocation(MODID, "book"), new GreedIslandProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Fetch & Copy Capability
        Player playerOld = event.getOriginal();
        Player playerNew = event.getEntity();
        IMoreHealth capOld = MoreHealth.getFromPlayer(playerOld);
        IMoreHealth capNew = MoreHealth.getFromPlayer(playerNew);
        INenUser nenOld = NenUser.getFromPlayer(playerOld);
        INenUser nenNew = NenUser.getFromPlayer(playerNew);
        ItemStackHandler bookOld = playerOld.getCapability(BOOK_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("Book can not be null"));
        ItemStackHandler bookNew = playerNew.getCapability(BOOK_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("Book can not be null"));
        bookNew.deserializeNBT(bookOld.serializeNBT());
        nenNew.copy(nenOld);
        capNew.copy(capOld);

//        int Type = nenNew.getNenType();
//        // Copy Health on Dimension Change
//        if (!event.isWasDeath()) {
////            if (Type == 1) {
////                HunterXHunter.applyHealthModifier(playerNew, capNew.getEnhancerModifier());
////            }
////            else {
////                HunterXHunter.applyHealthModifier(playerNew, capNew.getTrueModifier());
////            }
//            playerNew.setHealth(playerOld.getHealth());
//        }
    }

    @SubscribeEvent
    public static void onDevTesting(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if(event.getKey() == devTesting.getKey().getValue()) {
            Debug debug = new Debug();
            Minecraft.getInstance().setScreen(debug);
        }
        else if (event.getKey() == book.getKey().getValue()) {
            PacketManager.sendToServer(new SyncBookPacket(2, new CompoundTag()));
        }
    }

    @SubscribeEvent
    public static void onMainMenu(ScreenEvent.Init event) {
        if (event.getScreen() instanceof TitleScreen) {
            event.getScreen().getMinecraft().setScreen(new MainMenu());
        }
    }

}
